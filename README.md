
# 已安装应用列表查询展示

## 需求与功能
开发一个Android App ，应用包含：

1. 一个页面，可供用户输入应用的版本和APP名字(label)
2. 在用户输入过程中以列表实时显示符合条件的应用
3. 列表中需包含 App的icon、label、名称、版本、packagename
4. 要求应用信息信息写入数据库并在当前APP每次打开时跟新数据库

## 技术方案
整体框架使用MVVM，用到的技术框架：Room、LiveData、ViewModel
1. Dao层：
    - 整体方案使用Android官方推荐的Room作为数据存储方案；
    - 每次进入获系统已安装应用列表，进行数据库更新
2. ViewModel层：
    - 使用ViewModel获取数据；
    - 当查询条件变化时，发起查询，并用LiveData通知UI更新
3. 使用AndroidJUnit4对Dao层使用的接口进行单元测试
