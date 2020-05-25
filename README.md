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

# sql语句临时存储
/*create trigger insertFamily on familyinfo
after insert
as
declare @name varchar(10),@user varchar(12)
begin
select @name=inserted.familyname from inserted
select @user=inserted.familyown from inserted
exec upfamily @family_name=@name,@family_own=@user
end*/
/*create proc upfamily
@family_name varchar(10),@family_own varchar(10)
as begin
update UserInfo
set familyname=@family_name
where username=@family_own
end
*/
--select familyname from UserInfo where username='99'
/*create trigger create_Family on familyinfo
after insert
as
declare @name varchar(10)
begin
select @name=inserted.familyname from inserted
exec create_family_data @familyData=@name
end*/
/*create proc create_family_data
@familyData varchar(10)
as begin
declare @sql varchar(1000)
set @sql='create table '+@familyData+' ( number varchar(10) not null primary key, member varchar(10)not null,revenue varchar(10) not null,types varchar(10)not null,Amount float not null,tradeNotes varchar(53),time datetime);'
exec(@sql)
end*/
--print('create table '+'yu'+' ( number int not null primary key, member varchar(10)not null,revenue varchar(10) not null,types varchar(10)not null,Amount float not null,tradeNotes varchar(53),time);');
/*create trigger Sync on data44
after insert
as
declare @num varchar(10),@user varchar(10),@reve varchar(10),@type varchar(10),@amount float,@trade varchar(53),@t datetime
begin
select @reve=inserted.revenue ,@type=inserted.types,@amount=inserted.Amount,@trade=inserted.tradeNotes,@t=inserted.time from inserted
set @user='44'
set @num=select MAX(number) from
insert into yu values(@num,@user,@reve,@type,@amount,@trade,@t)
end*/