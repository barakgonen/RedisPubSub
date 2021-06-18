package org.bg.publisher;

import redis.clients.jedis.Jedis;

public class Main {
    public static void main(String[] args) {

        int numberOfChannels = 30000;
        int numberOfRuns = 1;

        Jedis jedis = null;

        String msg = "lkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvda";


        try {
            jedis = new Jedis();

            while (true) {
                for (int i = 0; i < numberOfChannels; i++) {
                    jedis.publish("Channel_" + i, msg + (i * numberOfRuns));
                }
                numberOfRuns++;

                if (numberOfRuns % 100 == 0)
                    System.out.println("Published: " + numberOfRuns
                            + " times to each channel, total msgs: " + (numberOfRuns * numberOfChannels));
            }

        } catch (Exception ex) {

            System.out.println("Exception : " + ex.getMessage());
        } finally {

            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
