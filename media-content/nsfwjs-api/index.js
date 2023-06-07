const express = require('express')
const multer = require('multer')
const jpeg = require('jpeg-js')

const tf = require('@tensorflow/tfjs-node')
const nsfw = require('nsfwjs')

const app = express()
const sharp = require('sharp')
const upload = multer({
    fileFilter: function (req, file, cb) {
        if (
            file.mimetype !== 'image/png' &&
            file.mimetype !== 'image/jpg' &&
            file.mimetype !== 'image/jpeg' &&
            file.mimetype !== 'image/gif'
        ) {
            cb(null, false);
        } else {
            cb(null, true);
        }
    }
})

let _model

const convert = async (img) => {
    // Decoded image in UInt8 Byte array
    const image = await jpeg.decode(img, { useTArray: true })

    const numChannels = 3
    const numPixels = image.width * image.height
    const values = new Int32Array(numPixels * numChannels)

    for (let i = 0; i < numPixels; i++)
        for (let c = 0; c < numChannels; ++c)
            values[i * numChannels + c] = image.data[i * 4 + c]

    return tf.tensor3d(values, [image.height, image.width, numChannels], 'int32')
}

app.post('/nsfw-check', upload.single('image'), async (req, res) => {
    if (!req.file) res.status(400).send('Missing image multipart/form-data')
    else {
        try {
            const data = await sharp(req.file.buffer)
                .jpeg()
                .toBuffer()
            const image = await convert(data)
            const predictions = await _model.classify(image)
            image.dispose()
            console.log(predictions)
            res.json(predictions)
        } catch (error) {
            res.json(400).send('Error')
        }
    }
})

const load_model = async () => {
    _model = await nsfw.load()
}

// Keep the model in memory, make sure it's loaded only once
load_model().then(() => app.listen(8888))
console.log('app server start at port 8888!')

// curl --request POST localhost:8080/nsfw --header 'Content-Type: multipart/form-data' --data-binary 'image=@/full/path/to/picture.jpg'