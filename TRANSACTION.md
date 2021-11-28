事物研究
===

古人学问无遗力，少壮工夫老始成。纸上得来终觉浅，绝知此事要躬行。

# 事务基础概念

# 事务四大特性

# 事务传播传播行为

 事务七大传播机制，五大隔离机制。

[详解事务的7种传播行为](https://blog.csdn.net/qq_34115899/article/details/115602002)

[Spring事务传播行为详解](https://segmentfault.com/a/1190000013341344)

![](https://img-blog.csdnimg.cn/20210411170246163.png)

子方法发生异常并且try catch捕获了异常，也会引起事务回滚，因为子方法和父方法是在 **同一个事务中** 。
![img.png](asset/img/transaction1.png)

> 事务回滚默认是只针对运行时异常，一般来说都会指定好针对的异常。由rollbackFor指定。

# 事务的五大隔离机制

[什么是脏读、不可重复读、幻读？](https://www.zhihu.com/question/458275373)

![](https://pic3.zhimg.com/80/v2-25ed812ff748a38bd3e4127db1ed7a48_720w.jpg)

# 事务应用场景

# 事务的失效场景

