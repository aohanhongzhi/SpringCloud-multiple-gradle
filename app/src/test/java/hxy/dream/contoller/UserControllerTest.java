package hxy.dream.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hxy.dream.BaseTest;
import hxy.dream.dao.model.UserModel;
import hxy.dream.entity.enums.GenderEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testUserAdd() throws Exception {

        UserModel stu = new UserModel();
        stu.setName("parent");
        stu.setAge(19);
        stu.setGender(GenderEnum.BOY);

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(stu);

        mvc.perform(post("/user/add/body")
                        .content(s) // 提交body参数
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
    }

}
