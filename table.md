
## 4. 数据库设计


## 系统服务URL表
### mt_service_url

列名 | 类型| 扩展属性 |说明
---|---|---|---
url| varchar(100) | key | 请求URL地址 
name| varchar(100) | null:false | URL名称 
app_id| varchar(100) | null:false | 应用ID 
data_auth_script| varchar(500) | default:'',null:false | 数据权限脚本 
|  |  |  


## 租户系统服务URL表
### mt_service_url_tenant

| 列名             | 类型         | 扩展属性              | 说明         |
| ---------------- | ------------ | --------------------- | ------------ |
| id               | bigint       | key                   | 主键         |
| tenant_id        | bigint       | null:false            | 所属         |
| app_id           | varchar(100) | null:false            | 所属应用     |
| url              | varchar(100) | null:false            | 权限URL      |
| data_auth_script | varchar(500) | default:'',null:false | 数据权限脚本 |
|                  |              |                       |              |
|                  |              |                       |              |



## 系统菜单权限

### mt_menu

| 列名         | 类型         | 扩展属性                | 说明                                   |
| ------------ | ------------ | ----------------------- | -------------------------------------- |
| id           | bigint       | key                     | 主键                                   |
| name         | varchar(100) | null:false              | 菜单名称                               |
| url          | varchar(100) | null:false              | 菜单路由                               |
| type         | tinyint      | null:false              | MenuType@菜单类型:0-菜单,1-页面,2-按钮 |
| pid          | bigint       | default:0,null:false    | 父级菜单ID                             |
| app_id       | varchar(100) | null:false              | 所属应用                               |
| service_urls | json         | default:'{}',null:false | 关联后端服务URL                        |





