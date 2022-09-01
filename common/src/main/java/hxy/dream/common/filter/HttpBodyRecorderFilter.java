package hxy.dream.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class HttpBodyRecorderFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(HttpBodyRecorderFilter.class);


    private static final int DEFAULT_MAX_PAYLOAD_LENGTH = 1024 * 512;

    private int maxPayloadLength = DEFAULT_MAX_PAYLOAD_LENGTH;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        boolean isFirstRequest = !isAsyncDispatch(request);

        HttpServletRequest requestToUse = request;

        if (isFirstRequest && !(request instanceof ContentCachingRequestWrapper)
                && (request.getMethod().equals(HttpMethod.PUT.name())
                || request.getMethod().equals(HttpMethod.POST.name()))) {
            requestToUse = new ContentCachingRequestWrapper(request);
        }

        HttpServletResponse responseToUse = response;

        if (!(response instanceof ContentCachingResponseWrapper) && (request.getMethod().equals(HttpMethod.PUT.name())
                || request.getMethod().equals(HttpMethod.POST.name()))) {
            responseToUse = new ContentCachingResponseWrapper(response);
        }

        boolean hasException = false;

        try {
            filterChain.doFilter(requestToUse, responseToUse);
        } catch (final Exception e) {
            hasException = true;
            throw e;
        } finally {
            int code = hasException ? 500 : response.getStatus();

            if (!isAsyncStarted(requestToUse) && (this.codeMatched(code, recordCode()))) {
                recordBody(createRequest(requestToUse), createResponse(responseToUse));
            } else {
                writeResponseBack(responseToUse);
            }

        }

    }

    protected String createRequest(HttpServletRequest request) {
        String payload = "";

        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);

        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            payload = genPayload(payload, buf, wrapper.getCharacterEncoding());
        }

        return payload;
    }

    protected String createResponse(HttpServletResponse resp) {
        String response = "";

        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(resp, ContentCachingResponseWrapper.class);

        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();

            try {
                wrapper.copyBodyToResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }

            response = genPayload(response, buf, wrapper.getCharacterEncoding());
        }

        return response;

    }

    protected void writeResponseBack(HttpServletResponse resp) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(resp, ContentCachingResponseWrapper.class);

        if (wrapper != null) {
            try {
                wrapper.copyBodyToResponse();
            } catch (IOException e) {
                log.error("Fail to write response body back", e);
            }
        }

    }

    private String genPayload(String payload, byte[] buf, String characterEncoding) {

        if (buf.length > 0 && buf.length < getMaxPayloadLength()) {
            try {
                payload = new String(buf, 0, buf.length, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
        }

        return payload;

    }

    public int getMaxPayloadLength() {
        return maxPayloadLength;
    }

    private boolean codeMatched(int responseStatus, String statusCode) {

        if (statusCode.matches("^[0-9,]*$")) {
            String[] filteredCode = statusCode.split(",");
            return Stream.of(filteredCode).map(Integer::parseInt).collect(Collectors.toList()).contains(responseStatus);
        } else {
            return false;
        }

    }

    protected abstract void recordBody(String payload, String response);

    protected abstract String recordCode();

}