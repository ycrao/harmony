media-content
-------------


### 图片检测API

基于 [nsfwjs](https://github.com/infinitered/nsfwjs) 实现的一个图片鉴黄服务。

```bash
cd project/nsfwjs-api
yarn
# 单跑
node index.js 
# 为了服务常驻，可以全局安装 pm2 使用
# npm i -g pm2
pm2 start index.js --name nsfwjs-api
```

#### 接口说明

>   只支持常规格式图片，如 `jpg/png/gif` 等。

```bash
# /root/xx.png 为图片地址
curl http://localhost:8888/nsfw-check -F "image=@/root/xx.png;type=image/jpeg"
```

返回信息：

```json
[
    {
        "className": "Neutral",
        "probability": 0.9277840852737427
    },
    {
        "className": "Drawing",
        "probability": 0.07143104821443558
    },
    {
        "className": "Hentai",
        "probability": 0.0007780276937410235
    },
    {
        "className": "Porn",
        "probability": 0.000005075656645203708
    },
    {
        "className": "Sexy",
        "probability": 0.0000018030658566203783
    }
]
```

类型参考：

- `Neutral` 和 `Drawing`：均为正常图片
- `Hentai`：二次元类型的暴露图片指数
- `Porn`：就是色情图片的指数
- `Sexy`：露点图片的指数

图片指数越高，越接近该类型，最高的基本可以判定为该类型。

备注：`res` 目录下图片大部分为 `stable-diffusion` 之类的 AI 生成图（仅选择软色情之类的图，为了测试全面请自行下载"硬图"），仅供测试使用。