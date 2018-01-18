package study.wzp.data;


import org.junit.Test;

import java.util.regex.Pattern;

public class AminoTest {

    /**
     * IPV4
     */
    static class Ipv4 {

        public final static long[] BIT_PARAMS = new long[]{
                1L << 24,
                1L << 16,
                1L << 8,
                1L
        };

        /** IP Pattern */
        public final static Pattern ipPattern = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}");

        /**
         * 转换成32bit Integer
         * @param ip 输入的IP地址
         * @return
         */
        public static long toInt32(String ip) {

            long int32Value = 0L;

            if (!validateIp(ip)){
                throw new IllegalArgumentException("IP Address Format Error, ip:" + ip);
            }

            String[] ipDigests = ip.split("\\.");

            // double check
            if (ipDigests.length != 4) {
                throw new IllegalArgumentException("IP Address Format Length Error, ip:" + ip);
            }

            int i = 0;
            for (String ipDigest : ipDigests) {
                int32Value += Integer.parseInt(ipDigest) * BIT_PARAMS[i];
                i ++;
            }

            return int32Value;
        }


        /**
         * 验证IP地址是否合法
         * @param ip
         * @return
         */
        private static boolean validateIp(String ip) {
            return ipPattern.matcher(ip).matches();
        }

    }

    /**
     * 验证ValidateIp函数
     */
    @Test
    public void testValidateIp() {

        String ip1 = "172 .168.5.1";
        assert Ipv4.validateIp(ip1) == false;

        String ip2 = "172. 168.5.1";
        assert Ipv4.validateIp(ip2) == false;

        String ip3 = "172.168.5 .1";
        assert Ipv4.validateIp(ip3) == false;

        String ip4 = "172.168.5.1 ";
        assert Ipv4.validateIp(ip4) == false;

        String ip5 = "172.168.5.1";
        assert Ipv4.validateIp(ip5) == true;

    }

    /**
     * 测试{@link Ipv4#toInt32(String)} success
     */
    @Test
    public void testToInt32ForSuccess() {
        assert Ipv4.toInt32("172.168.5.1") == 2896692481L;
    }

    /**
     * 测试{@link Ipv4#toInt32(String)} failure
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToInt32ForException() {
        assert Ipv4.toInt32("172.168. 5.1") == 2896692481L;
    }


}
