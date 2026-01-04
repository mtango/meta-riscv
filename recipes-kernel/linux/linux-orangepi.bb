SUMMARY = "Orange Pi Linux Kernel"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

DEPENDS:append:x1-orangepi = " u-boot-tools-native"

LIC_FILES_CHKSUM:x1-orangepi = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

BRANCH:x1-orangepi = "orange-pi-6.6-ky"
SRCREV:x1-orangepi = "ae9e974d3e19f460b6397bfe8f0f1417a073ce05"

LINUX_VERSION:x1-orangepi = "6.6.63"
LINUX_VERSION_EXTENSION:append:orangepi-rv2 = "-orangepi-rv2"
LINUX_VERSION_EXTENSION:append:orangepi-r2s = "-orangepi-r2s"

PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://github.com/orangepi-xunlong/linux-orangepi.git;protocol=https;branch=${BRANCH}"

SRC_URI:append:x1-orangepi = " file://disable-focaltech-touchscreen.cfg \
				file://disable-rtl8852bs.cfg \
				file://bcmdhd-fix-typedefs-header-not-found.patch"

INITRAMFS_IMAGE:x1-orangepi = "core-image-minimal-initramfs"

KCONFIG_MODE = "alldefconfig"
KBUILD_DEFCONFIG:x1-orangepi = "x1_defconfig"

KERNEL_FEATURES:remove:riscv32:x1-orangepi = " ${KERNEL_FEATURES_RISCV}"
KERNEL_FEATURES:remove:riscv64:x1-orangepi = " ${KERNEL_FEATURES_RISCV}"

do_install:append:x1-orangepi() {
	sed -i -e 's#${S}##g' ${B}/drivers/tty/vt/consolemap_deftbl.c
}

do_deploy:append:x1-orangepi() {
	cd ${DEPLOY_DIR_IMAGE}
	mkimage -A riscv -O linux -T ramdisk -n "Initial Ram Disk" \
		-d ${INITRAMFS_IMAGE}-${MACHINE}.cpio.gz initramfs.img
}

COMPATIBLE_MACHINE = "^x1-orangepi$"
