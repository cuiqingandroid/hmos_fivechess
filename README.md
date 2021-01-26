基于鸿蒙OS开发五子棋，自带简单AI

此代码从之前安卓代码移植过来，安卓带局域网对战功能，限于目前鸿蒙真机环境相对欠缺，等鸿蒙稍微普及一点，再做移植。

如对局域网对战有兴趣，[可点此](https://github.com/cuiqingandroid/FiveChess)

## 开发环境
[开发官网](https://developer.harmonyos.com/cn/develop)

[官方论坛](https://developer.huawei.com/consumer/cn/forum/blockdisplay?fid=0101303901040230869)

## 应用包名
`com.txxia.fivechess`

## 真机调试
1. [参考官网](https://developer.harmonyos.com/cn/docs/documentation/doc-guides/debug_overview-0000001053822404)配置好
2. 添加应用时包名填`com.txxia.fivechess`
3. 修改`entry`中的`build.gradle`里的`signingConfigs`为你自己的证书

## 涉及知识点
- Ablity,Slice
- 自定义view
- canvas画布
- 位图加载
- xml布局
- AI算法

## 已知问题
第一次进去棋谱画面，`component`大小计算不能实时渲染，这点跟`Android`的`View`的layout机制不一样。

等待鸿蒙进一步开源，查看具体实现。

## 开中过程遇到的问题
- 开发文档并不很全，很多功能无从下手
- 开源的代码也不全，无法查看源码（在没有文档的时候很无助）
- 远程模拟器调试太慢
- 真机调试步骤略显繁琐（花了2-3个小时才搞定）

## 给鸿蒙的一些建议
- 文档增加中文版，毕竟刚开始大部分开发者都是中国人吧？
- 社区活跃度低，回答时效也相对较低，缺乏专业技术人员

## 关于鸿蒙
作为第一批开发安卓的开发者，鸿蒙目前已经做的很好的。

当年被乔布斯骂抄袭的安卓，如今遍地开花。

如果有朝一日，鸿蒙干死安卓，不知乔老爷子地下有何感想

