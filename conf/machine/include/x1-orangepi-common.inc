#@TYPE: Include
#@NAME: x1-orangepi-common
#@DESCRIPTION: Common configuration for Ky X1 OrangePi boards

require conf/machine/include/riscv/tune-riscv.inc

PREFERRED_PROVIDER_virtual/kernel ?= "linux-orangepi"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-orangepi"

UBOOT_MACHINE = "x1_defconfig"
SPL_BINARY = "spl/u-boot-spl.bin"
UBOOT_ENV = "boot"
UBOOT_ENV_SUFFIX = "scr"

RISCV_SBI_PLAT = "generic"

KERNEL_CLASSES = "kernel"
KERNEL_IMAGETYPE = "Image"

EXTRA_IMAGEDEPENDS += "u-boot-orangepi"

INITRAMFS_FSTYPES = "cpio.gz"
IMAGE_FSTYPES += "wic.gz wic.bmap ext4"

WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"

SERIAL_CONSOLES = "115200;ttyS0"
MACHINE_EXTRA_RRECOMMENDS += "kernel-modules linux-firmware-orangepi"
