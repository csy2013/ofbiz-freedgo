

JAVA_OPTS="-server -showversion -Xms12g -Xmx12g -XX:PermSize=800m -XX:MaxPermSize=512m  -Xms2048M -Xmx2048M "
JAVA_OPTS="$JAVA_OPTS -d64 -XX:CICompilerCount=8 -XX:+UseCompressedOops"
JAVA_OPTS="$JAVA_OPTS -XX:SurvivorRatio=4 -XX:TargetSurvivorRatio=90"
JAVA_OPTS="$JAVA_OPTS -XX:ReservedCodeCacheSize=512m -XX:-UseAdaptiveSizePolicy"
JAVA_OPTS="$JAVA_OPTS -Duser.timezone=Asia/Shanghai -XX:-DontCompileHugeMethods"
JAVA_OPTS="$JAVA_OPTS -Xss256k -XX:+AggressiveOpts -XX:+UseBiasedLocking"
JAVA_OPTS="$JAVA_OPTS -XX:MaxTenuringThreshold=31 -XX:+CMSParallelRemarkEnabled "
JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=512m -XX:+UseFastAccessorMethods"
JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSInitiatingOccupancyOnly -Djava.awt.headless=true"
JAVA_OPTS="$JAVA_OPTS -XX:+UseGCOverheadLimit -XX:AllocatePrefetchDistance=256 -XX:AllocatePrefetchStyle=1"
JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:MaxGCPauseMillis=200"

//替换生产环境：
sed -i "s/\/Users\/tusm\/javaProject\/svn\/yabiz/\/home\/yabiz/g" `grep \/Users\/tusm\/javaProject\/svn\/yabiz -rl /home/yabiz/daojia_muti`

//替换生产数据库配置
cp /home/daojia/framework/entity/config/entityengine.xml /home/yabiz/daojia_muti/daojia/daojia/docker/base/config/


//生产机compenent 增加：/home/yabiz/daojia_muti/daojia/daojia/docker/base/config/component-load.xml
    <load-components parent-directory="/home/mall/yabiz1.0/mobile"/>
    <load-components parent-directory="/home/mall/yabiz1.0/specialpurpose"/>
    <load-components parent-directory="/home/daojia/hot-deploy"/>
    <load-components parent-directory="/home/mall/yabiz1.0/ecommerce"/>



