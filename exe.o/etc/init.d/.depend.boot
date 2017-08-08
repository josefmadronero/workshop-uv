TARGETS = console-setup mountkernfs.sh resolvconf ufw hostname.sh plymouth-log x11-common apparmor screen-cleanup udev keyboard-setup cryptdisks cryptdisks-early checkroot.sh lvm2 checkfs.sh iscsid networking urandom hwclock.sh mountdevsubfs.sh rpcbind open-iscsi mountall.sh checkroot-bootclean.sh mountall-bootclean.sh bootmisc.sh mountnfs-bootclean.sh mountnfs.sh kmod procps
INTERACTIVE = console-setup udev keyboard-setup cryptdisks cryptdisks-early checkroot.sh checkfs.sh
udev: mountkernfs.sh
keyboard-setup: mountkernfs.sh udev
cryptdisks: checkroot.sh cryptdisks-early udev lvm2
cryptdisks-early: checkroot.sh udev
checkroot.sh: hwclock.sh keyboard-setup mountdevsubfs.sh hostname.sh
lvm2: cryptdisks-early mountdevsubfs.sh udev
checkfs.sh: cryptdisks lvm2 checkroot.sh
iscsid: networking
networking: mountkernfs.sh urandom resolvconf procps
urandom: hwclock.sh
hwclock.sh: mountdevsubfs.sh
mountdevsubfs.sh: mountkernfs.sh udev
rpcbind: networking
open-iscsi: networking iscsid
mountall.sh: checkfs.sh checkroot-bootclean.sh lvm2
checkroot-bootclean.sh: checkroot.sh
mountall-bootclean.sh: mountall.sh
bootmisc.sh: mountall-bootclean.sh mountnfs-bootclean.sh checkroot-bootclean.sh udev
mountnfs-bootclean.sh: mountnfs.sh
mountnfs.sh: networking rpcbind
kmod: checkroot.sh
procps: mountkernfs.sh udev
