### 课程直播-demo

- 此demo主要展示课程直播相关功能，包括创建直播课程、创建场景群、获取观看直播人数等功能。
- 此demo为三方企业应用，需要开发者在钉钉开发后台创建场景群模版。
- 项目结构
  - rear-end：后端模块，springboot构建，功能接口功能包括：创建场景群、创建直播课程、获取观看直播人数等，其中模块中还包含了三方应用必需的推送回调接口。
  - front-end：前端模块，react构建，场景功能包括：自动登录，创建直播课程、创建场景群、获取直播观看结果等。

### 研发环境准备

1. 需要有一个钉钉注册企业，如果没有可以创建：https://oa.dingtalk.com/register_new.htm#/

2. 成为钉钉开发者，参考文档：https://developers.dingtalk.com/document/app/become-a-dingtalk-developer

3. 登录钉钉开放平台后台创建一个第三方企业H5微应用： https://open-dev.dingtalk.com/fe/app#/isv/app

4. 配置应用

   配置开发管理，参考文档：https://developers.dingtalk.com/document/app/basic-configuration

   - **此处配置“应用首页地址”需公网地址，若无公网ip，可使用钉钉内网穿透工具：**

     https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration

![1](https://img.alicdn.com/imgextra/i1/O1CN010uGazP1mNU4VYDXjL_!!6000000004942-2-tps-1440-714.png)



配置相关权限：https://developers.dingtalk.com/document/app/address-book-permissions

本demo使用接口相关权限：

”成员信息读权限“、“chat相关接口的管理权限”、“直播培训平台读权限”、“直播培训平台写权限”

![2](https://img.alicdn.com/imgextra/i2/O1CN017EjCGQ1C9RlSgNwXI_!!6000000000038-2-tps-1440-657.png)

5. 创建场景群模版：https://developers.dingtalk.com/document/chatgroup/fill-in-a-group-template

   ![](https://img.alicdn.com/imgextra/i3/O1CN01SjFrwQ1jIxITqcA6S_!!6000000004526-2-tps-1212-249.png)


### 运行前准备

 下载demo

```shell
git clone https://github.com/open-dingtalk/h5app-live-streaming-demo.git
```

### 获取相应参数

获取到以下参数，修改后端application.yaml

```yaml
app:
  suite-ticket: ****
  suite-key: ****
  suite-secret: ****
  app-id: ****
  token: ****
  encoding-aes-key: ****
  group-template-id: ****

```

参数获取方法：登录开发者后台

2. 进入应用开发-第三方企业应用-点击进入应用-基础信息-获取suite-key、suite-secret、app-id
2. 进入应用开发-第三方企业应用-点击进入应用-开发管理-获取配置的token、encoding-aes-key
3. 获取suite-ticket：通过配置“数据推送”获取（https://developers.dingtalk.com/document/app/configure-synchttp-push）

### 修改前端页面

**打开项目，命令行中执行以下命令，编译打包生成build文件**

```shell
cd frontend
npm install
npm run build
```

**将打包好的静态资源文件放入后端**

![3](https://img.alicdn.com/imgextra/i4/O1CN01DXYZDM1ZlfzEXX9fr_!!6000000003235-2-tps-378-498.png)

### 启动项目

- 启动springboot
- 移动端钉钉点击工作台，找到创建的应用，访问应用

### 运行展示

![4](https://img.alicdn.com/imgextra/i4/O1CN01qG31Cj23BtW9RBPvB_!!6000000007218-2-tps-500-319.png)

创建课程

![5](https://img.alicdn.com/imgextra/i4/O1CN01BuR2nc1E9Z6E9iIMJ_!!6000000000309-2-tps-500-361.png)

创建直播群

![7](https://img.alicdn.com/imgextra/i4/O1CN01vbgDAs1iWPKCSTyXn_!!6000000004420-2-tps-498-993.png)

查看直播

![9](https://img.alicdn.com/imgextra/i1/O1CN01m34Xid1FbKjHQ6OmH_!!6000000000505-2-tps-573-1098.png)

直播中

![8](https://img.alicdn.com/imgextra/i1/O1CN01fc42TL1f7j8SopltS_!!6000000003960-2-tps-491-460.png)

直播统计

![10](https://img.alicdn.com/imgextra/i1/O1CN01zfnPVP21Dblk4A0mz_!!6000000006951-2-tps-495-494.png)

直播数据



### 参考文档

1. 配置第三方企业应用数据推送，文档链接：https://developers.dingtalk.com/document/app/configure-synchttp-push
2. 获取企业永久授权码，文档链接：http://developers.dingtalk.com/document/app/obtain-a-permanent-authorization-code
3. 激活授权的企业，文档链接：https://developers.dingtalk.com/document/app/activate-suite
4. 获取第三方企业应用access_token，文档链接：https://developers.dingtalk.com/document/app/obtains-the-enterprise-authorized-credential
5. 创建场景群，文档链接：https://developers.dingtalk.com/document/chatgroup/create-a-scene-group-v2
6. 创建培训课程，文档链接：https://developers.dingtalk.com/document/app/create-live-courses
7. 获取培训观看数据，文档链接：https://developers.dingtalk.com/document/app/obtains-the-playback-data-of-a-live-stream
