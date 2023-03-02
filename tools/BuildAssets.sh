#!/bin/sh
Img=ic_launcher.png

cd ./res

for Dpi in m h xh xxh xxxh
do
	mkdir -p ./drawable-${Dpi}dpi
done

convert ./drawable/$Img -resize 48x48 ./drawable-mdpi/$Img
convert ./drawable/$Img -resize 72x72 ./drawable-hdpi/$Img
convert ./drawable/$Img -resize 96x96 ./drawable-xhdpi/$Img
convert ./drawable/$Img -resize 144x144 ./drawable-xxhdpi/$Img
convert ./drawable/$Img -resize 192x192 ./drawable-xxxhdpi/$Img

for Dpi in m h xh xxh xxxh
do
	pngquant -v --strip --speed=1 --force -o ./drawable-${Dpi}dpi/$Img ./drawable-${Dpi}dpi/$Img
done

cd ..
