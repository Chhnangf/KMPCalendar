This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…



calendar
- [x] 模式切换（周历/月历）
- [x] 日历滚动（上周/上月/下周/下月）
- [x] 今日标记
- [x] 选择日期
- [ ] 为选择的日期增加状态
- [ ] 日历额外扩展：添加自定义事件、倒计时、提醒等

charts
- [x] bar
- [ ] 曲线（单选-填充）
- [ ] 曲线（多选-线段）

chart style extension
- [ ] 渐变颜色
- [ ] 贝塞尔曲线