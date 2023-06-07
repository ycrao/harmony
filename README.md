Harmony
-------

>   Harmony [和谐]

### 敏感词检测与过滤方案

- [houbb/sensitive-word](https://github.com/houbb/sensitive-word) ：`Java` 语言基于 `DFA` 算法实现。
- [lsj575/wordfilter](https://github.com/lsj575/wordfilter) ：`Golang` 语言基于 `Trie` 树结构实现。
- [importcjj/sensitive](https://github.com/importcjj/sensitive/blob/Aho-Corasick/README.md) ：`Golang` 语言基于 `AC自动机` 实现。
- [toolgood/ToolGood.Words](https://github.com/toolgood/ToolGood.Words) ：多语言，该作者自叙采用优化之后的 `DFA` 算法，内存占用更小，检测速度更快。
- [observerss/textfilter](https://github.com/observerss/textfilter) ： `Python` 语言版本。
- [ZhelinCheng/mint-filter](https://github.com/ZhelinCheng/mint-filter) ， [liuxueyong123/sensitive-word-tool](https://github.com/liuxueyong123/sensitive-word-tool)： `JavaScript` 版本。

#### 算法说明

- [Trie](https://zh.wikipedia.org/zh-cn/Trie) 树，算法可视化参考这个[链接](https://www.cs.usfca.edu/~galles/visualization/Trie.html)。
- [AC自动机](https://zh.wikipedia.org/zh-cn/AC%E8%87%AA%E5%8A%A8%E6%9C%BA%E7%AE%97%E6%B3%95) ，完整名：`Aho–Corasick` 自动机。
- [DFA](https://zh.wikipedia.org/wiki/%E7%A1%AE%E5%AE%9A%E6%9C%89%E9%99%90%E7%8A%B6%E6%80%81%E8%87%AA%E5%8A%A8%E6%9C%BA)，中文名：确定有限状态自动机，是一个比较抽象的概念，可以认为上述2种算法是它的具体实现。

### 媒体检测与过滤方案

图片黄暴检测，一般需要借助于深度学习框架（如 `TensorFlow` 等）进行大量训练后，方能得出较好识别效果。视频内容也可简化成抽出特定间歇帧图片后再检测，而音频内容则会涉及语音识别（包括方言）再转换文字后进行过滤，另外环境人声音色与语调等也是要考虑到的。

最快捷简单的办法就是接入腾讯、阿里与百度等云安全检测接口。

参考资源：

- 训练数据集：[EBazarov/nsfw_data_source_urls](https://github.com/EBazarov/nsfw_data_source_urls) [alex000kim/nsfw_data_scraper](https://github.com/alex000kim/nsfw_data_scraper)
- [infinitered/nsfwjs](https://github.com/infinitered/nsfwjs)
- [视频/图片鉴黄有哪些算法和开源库参考？](https://www.zhihu.com/question/40382198)
- [人工智能鉴黄的原理是什么？](https://www.zhihu.com/question/59424296)