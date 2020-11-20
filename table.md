[TOC]

# 功能权限&数据权限详细设计文档

## 1. 功能权限设计

![image-20201116152316687](权限设计文档.assets/image-20201116152316687.png)


## 2. 数据权限设计

![image-20201116153147817](权限设计文档.assets/image-20201116153147817.png)


## 3. 数据结构设计

### 3.1 系统服务URL权限

```json
{
    group:"用户管理",
    name:"添加用户",
	url:"/user/add",
    appId:"srm",
	dataAuthScript:"数据权限配置"
}
```



### 3.2  租户服务URL权限

```json
{
	tenantId:"",
    appId:"",
    group:"用户管理",
    name:"添加用户",
	url:"/user/add",
	dataAuthScript:"数据权限配置"
}
```



### 3.3  系统菜单权限



```json
{
    id:"",
    name:"",
    url:"",
    type:"菜单,页面,按钮"
    pid:"",
    appId:"",
    serviceUrls:[
    	""
    ]
}
```



### 3.4  租户自定义菜单权限



```json
{
   	id:"",
    tenantId:"",
    name:"",
    url:"",
    type:"菜单,页面,按钮"
    pid:"",
    appId:"",
    serviceUrls:[
    	""
    ]
}
```

### 3.5 租户菜单权限

```json
{
	id:"",
	tenantId:"",
	menuId:"",
    name:"",
    url:"",
    type:"菜单,页面,按钮"
    pid:"",
    appId:"",
    serviceUrls:[
    	""
    ]
}
```



### 3.6 用户菜单权限

```json
{
	id:"",
	tenantId:"",
	menuId:"",
	userId:"",
	appId:"",
    dataAuthScript:""
}
```



### 3.7 用户服务URL权限

```json
{
	id:"",
	tenantId:"",
    userId:"",
    appId:"",
    name:"",
    url:"",
    dataAuthScript:""
}
```



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





