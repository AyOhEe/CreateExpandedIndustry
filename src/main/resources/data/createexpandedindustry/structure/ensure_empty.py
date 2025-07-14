from nbt import nbt


def find_nbt_files():
    return ["hardened_erythrite_patch_small.nbt",
            "hardened_erythrite_patch_medium.nbt",
            "hardened_erythrite_patch_large.nbt",
            "hardened_erythrite_patch_gargantuan.nbt"]


def main():
    for file in find_nbt_files():
        print(f"Patching \"{file}\"...")
        nbtfile = nbt.NBTFile(file, "rb")

        for block in nbtfile["blocks"]:
            if block["nbt"]["id"].value == "createexpandedindustry:hardened_erythrite":
                block["nbt"]["remaining_resources"].value = -1

        nbtfile.write_file()

if __name__ == "__main__":
    main()