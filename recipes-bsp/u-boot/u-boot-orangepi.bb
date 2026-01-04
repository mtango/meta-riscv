require recipes-bsp/u-boot/u-boot.inc
require recipes-bsp/u-boot/u-boot-common.inc

DEPENDS += "u-boot-tools-native"
DEPENDS:append:x1-orangepi = " opensbi"

LIC_FILES_CHKSUM:x1-orangepi = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

BRANCH:x1-orangepi = "v2022.10-ky"
SRCREV:x1-orangepi = "89bff4a7e4cadfb5f130edb1ec44c39bff20a427"

SRC_URI = "git://github.com/orangepi-xunlong/u-boot-orangepi.git;protocol=https;branch=${BRANCH}"
SRC_URI:append:x1-orangepi = " file://drop-intree-opensbi-build.patch \
				file://boot.cmd"

SRC_URI:remove:riscv32:x1-orangepi = " ${SRC_URI_RISCV}"
SRC_URI:remove:riscv64:x1-orangepi = " ${SRC_URI_RISCV}"

do_configure:prepend:x1-orangepi() {
	mkimage -A riscv -O linux -T script -C none -n "U-Boot boot script" \
		-d ${UNPACKDIR}/${UBOOT_ENV}.${UBOOT_ENV_SRC_SUFFIX} ${UNPACKDIR}/${UBOOT_ENV_BINARY}
}

do_deploy:append:x1-orangepi() {
	install -m 644 ${S}/bootinfo_sd.bin ${DEPLOYDIR}/
	cp ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${B}/
	cp ${B}/arch/riscv/dts/x1_orangepi-rv2.dtb ${B}/
	# u-boot-nodtb.bin also needed for .itb but already in ${B}
	cp ${S}/uboot-opensbi.its ${B}/
	cd ${B}
	mkimage -f uboot-opensbi.its -r u-boot-opensbi.itb
	install -m 644 u-boot-opensbi.itb ${DEPLOYDIR}/
}

COMPATIBLE_MACHINE = "x1-orangepi"
