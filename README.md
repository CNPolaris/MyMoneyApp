# MyMoneyApp
数据库课程设计作业 基于Android的记账软件
# 进度
## 截至2020/4/23
**初步掌握了Android studio通过ip连接本地数据库的方法**,**以及如何使用菜单组件**
但是需要继续进行界面的美化,下一步将完成登录功能,以及进行数据库的设计
**gg今天啥都没干,数据库没设计好,app界面也没做完,就加上了位置固定**
# 数据库设计
**userinfo表**存放所有的用户的姓名和信息,有三个属性:
1. 用户名 ***username***
2. 密码 ***password***
3. 家庭 ***family***(允许为空,用户可自定义是否加入家庭)
每个用户在注册时都会新建在数据库中新建两个表***username+datain***和 ***username+dataout***
每个家庭也会有两个表***family+datain*** 和***family+dataout***
用户可以建立一个新的***family***,建立后会把自己的***datain***和***dataout***复制到***family***的表中,但是一个用户只能同时有一个***family***。
## usernamedatain和usernamedataout的属性
### usernamedatain 收入
属性名|数据类型|空值|主外键
:-|:-|:-|:-
|1. 用户名|varchar | false|true
|2. 工资|double |true |false
|3. 红包|double |true |false
|4. 理财|double |true |false
|5. 时间|datetime |false |false
|6. 备注|txt |true |false
### usernamedataout 支出
属性名|数据类型|空值|主外键
:-|:-|:-|:-
|1. 用户名|varchar | false|true
|2. 生活|double |true |false
|3. 购物|double |true |false
|4. 学习| double |true |false
|5. 娱乐|double |true |false
|6. 时间| datetime |false |false
|7. 备注| txt |true |false
### familydatain 家庭收入
属性名|数据类型|空值|主外键
:-|:-|:-|:-
|1. 家庭名|varchar | false|true
|2. 成员|varchar | false|外键
|3. 工资|double |true |false
|4. 红包|double |true |false
|5. 理财|double |true |false
|6. 时间|datetime |false |false
|7. 备注|txt |true |false
### familydatain 家庭支出
属性名|数据类型|空值|主外键
:-|:-|:-|:-