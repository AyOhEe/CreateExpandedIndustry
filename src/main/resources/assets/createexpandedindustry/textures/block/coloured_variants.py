from PIL import Image
import numpy as np

colour_sets = [
    ("light_gray", 208, 208, 208),
    ("gray", 138, 138, 138),
    ("black", 59, 59, 64),
    ("brown", 156, 113, 83),
    ("red", 181, 64, 64),
    ("orange", 234, 173, 83),
    ("yellow", 234, 234, 77),
    ("lime", 149, 218, 65),
    ("green", 100, 129, 56),
    ("cyan", 57, 123, 149),
    ("light_blue", 150, 185, 234),
    ("blue", 60, 109, 181),
    ("purple", 189, 124, 221),
    ("magenta", 225, 143, 218),
    ("pink", 240, 181, 211)
]

def mul_rgb(arr, rm, gm, bm):
    r, g, b, a = np.rollaxis(arr, axis=-1)
    r *= rm / 256.0
    g *= gm / 256.0
    b *= bm / 256.0
    arr = np.dstack((r, g, b, a))
    return arr

def colorize(image, rm, gm, bm):
    img = image.convert('RGBA')
    arr = np.array(np.asarray(img).astype('float'))
    new_img = Image.fromarray(mul_rgb(arr, rm, gm, bm).astype('uint8'), 'RGBA')

    return new_img

def main():
    fname = input("White variant of file to colourise: ")
    file_format = input("Format of new file names (python format string): ")
    try:
        with Image.open(fname, "r", ["png"]) as img:
            for (name, rm, gm, bm) in colour_sets:
                colorize(img, rm, gm, bm).save(file_format.format(name=name), "png")
    except:
        print("Something went wrong - sorry!")

main()