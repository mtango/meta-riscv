# Install RCPU firmware into the initramfs which is needed for Ky X1 OrangePis
PACKAGE_INSTALL:append:x1-orangepi = " firmware-rcpu-x1-orangepi"

# Add riscv64-oe-linux as a compatible hosts
COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*|riscv64.*)-(linux.*|freebsd.*)'
