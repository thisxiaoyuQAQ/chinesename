# 🌟 ChineseName - 中文名插件 🌟

<p align="center">
  <img src="https://img.shields.io/badge/version-2.66666%20卡拉比丘限定版-blue.svg" />
  <img src="https://img.shields.io/badge/author-%E5%B0%8F%E9%9B%A8-orange.svg" />
  <img src="https://img.shields.io/badge/licence-MIT-green.svg" />
</p>

<p align="center">
  <strong>🥇 中文名插件 | 🎮 卡拉比丘限定版 | 💖 小雨出品</strong>
</p>

---

> 初中时期作品 过于古早 屎山是正常的
> xD
## 🚀 项目简介

**ChineseName** 是一个功能超级强大的Minecraft服务器插件，可以让玩家拥有炫酷的中文名！不仅仅支持改名，还集成了传送系统、改名卡系统、黑名单过滤等超多功能！

### 🎯 核心特色

- ✨ **炫酷中文名** - 支持颜色代码，打造独一无二的身份标识
- ~~🎮 **传送系统** - 通过中文名进行传送（支持请求/接受）一直没写完嘻嘻~~
- 🃏 **改名卡系统** - 可交易的改名道具，增加服务器经济活力
- 🔒 **黑名单过滤** - 内置违规词汇过滤系统
- 🛡️ **权限分组** - 细粒度的权限控制，支持临时改名
- 📊 **bStats统计** - 内置数据统计，了解插件使用情况
- 🌐 **PAPI支持** - PlaceholderAPI集成，支持变量替换

---

## 📦 安装与配置

### 🔧 安装步骤

1. 下载插件JAR文件
2. 放入服务器 `plugins/` 目录
3. 重启/重载服务器
4. 配置文件自动生成在 `plugins/ChineseName/`

### 📝 配置文件详解

#### `settings.yml` - 核心配置

```yaml
tablist: true              # 是否在Tab列表显示中文名
maxlength: 20              # 最大字符长度限制
material: "NAME_TAG"       # 改名卡物品类型
name: "§d§l改名卡"         # 改名卡显示名
lores:                     # 改名卡描述
  - "§e食用方法:"
  - "§e/cn usecard 名称"
  - "§d114514"
blacklist:                 # 黑名单词汇
  - "sb"
  - "cnm"
```

#### `language.yml` - 语言配置

完全可自定义的消息提示！支持颜色代码（使用`&`符号）

---

## 🎮 使用指南

### 📖 帮助菜单

```
/cn help 1    # 查看基础功能
/cn help 2    # 查看增强功能
```

### 🔑 基础命令

| 命令 | 权限 | 描述 |
|------|------|------|
| `/cn set 名称` | `cn.set` | 设置自己的中文名 |
| `/cn set 名称 -t` | `cn.set.temporarily` | 设置临时中文名 |
| `/cn setother ID 名称` | `cn.setother` | 设置他人中文名 |
| `/cn reset ID` | `cn.reset` | 重置他人中文名 |
| `/cn check ID` | `cn.check` | 查询玩家中文名 |

### ⭐ 增强功能

| 命令 | 权限 | 描述 |
|------|------|------|
| `/cn givecard ID` | `cn.givecard` | 给予改名卡 |
| `/cn usecard 名称` | `cn.usecard` | 使用改名卡 |
| `/cn tp 中文名` | `cn.tp` | 传送到中文名玩家 |
| `/cn tpa 中文名` | `cn.tpa` | 请求传送 |
| `/cn tpaccept` | - | 接受传送请求 |
| `/cn reload` | `cn.reload` | 重载配置 |

### 📋 权限列表

```
cn.set                      # 设置自己的中文名
cn.set.temporarily          # 设置临时中文名
cn.setother                 # 设置他人的中文名
cn.setother.temporarily     # 设置他人临时中文名
cn.reset                    # 重置中文名
cn.reset.temporarily        # 重置临时中文名
cn.check                    # 查询中文名
cn.givecard                # 给予改名卡
cn.usecard                 # 使用改名卡
cn.tp                      # 中文名传送
cn.tpa                     # 请求传送
cn.maxlength.bypass        # 绕过长度限制
cn.blacklist.bypass        # 绕过黑名单
cn.reload                  # 重载插件
```

---

## 🎨 PlaceholderAPI 变量

安装PlaceholderAPI后，支持以下变量：

```
%cn_name%        # 获取玩家中文名
```

**使用示例：**
```
[中文名] %cn_name%[/中文名]
```

---

## 🔧 高级功能

### 🏪 改名卡系统

改名卡是一个可自定义的物品，可以：
- 通过 `/cn givecard` 发送给玩家
- 玩家使用 `/cn usecard 新名称` 来改名
- 支持黑名单检测
- 支持长度限制

### 🚀 传送系统

支持通过中文名进行传送，包含：
- **/cn tp 名称** - 直接传送
- **/cn tpa 名称** - 请求传送（需对方接受）
- **/cn tpaccept** - 接受请求

### 🔒 黑名单系统

自动过滤违规词汇，支持：
- 不区分大小写的匹配
- 多个关键词同时检测
- 管理员可绕过 (`cn.blacklist.bypass`)

---

## 📊 技术规格

### 🏗️ 项目结构

```
src/
├── com/cryxiaoyu/chinesename/
│   ├── Main.java                    # 主类 & 事件监听
│   ├── command/
│   │   └── NameChanger.java         # 命令处理器
│   ├── metrics/
│   │   ├── Metrics.java             # bStats统计
│   │   └── PlaceHolderApiHooker.java # PAPI集成
│   └── event/
│       ├── ChineseNameChangeEvent.java (已注释)
│       └── Tpa.java                 # 传送系统
├── plugin.yml                       # 插件描述
├── settings.yml                     # 核心配置
├── language.yml                     # 消息配置
└── config.yml                       # 示例配置
```

### 🔗 依赖

- **CraftBukkit/Spigot/Paper** 1.8+
- **PlaceholderAPI** (可选) - 用于变量支持

---

## ⚠️ 注意事项

1. **重载问题** - 插件不支持热重载，改名后需要重新进入服务器才能生效
2. **中文名传送** - 需要玩家在线才能通过中文名传送
3. **临时改名** - 服务器重启后临时改名会丢失（符合预期设计）

---

## 💡 最佳实践

### 🎓 使用建议

1. **权限组配置** - 根据玩家等级分配不同的改名权限
2. **改名卡经济** - 将改名卡作为商店商品，增加服务器经济
3. **黑名单维护** - 定期更新黑名单词汇库
4. **消息自定义** - 根据服务器风格调整语言配置

### 🔍 常见问题

**Q: 为什么改名后Tab列表没更新？**
A: 检查 `settings.yml` 中的 `tablist: true`

**Q: 如何让管理员绕过限制？**
A: 给予 `cn.maxlength.bypass` 和 `cn.blacklist.bypass` 权限

**Q: 传送命令报错？**
A: 确保目标玩家在线，且使用的是正确的中文名

---

## 🤝 支持与反馈

- 📧 **作者**: 小雨
- 🎫 **QQ**: 3066156386
- 💬 **反馈群**: 426996480 ⭐
- 💖 **爱发电**: https://afdian.com/a/ixiaoyu

---

## 📄 许可证

MIT License - 2024 小雨

---

## 🌈 版本历史

| 版本 | 更新内容 |
|------|----------|
| **2.66666** | 🎮 卡拉比丘限定版 - 新增中文名传送功能 |
| **2.4** | 测试版 - 支持中文名传送、自定义回复语句 |
| **早期版本** | 基础改名、改名卡、黑名单系统 |

---

<div align="center">

**如果这个插件帮助到了你，别忘了给个 ⭐ Star！**

Made with ❤️ by 小雨

</div>
