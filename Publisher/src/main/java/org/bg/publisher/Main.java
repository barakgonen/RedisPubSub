package org.bg.publisher;

import com.google.gson.Gson;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        int numberOfChannels = 5000;
//        int numberOfChannels = 100;

        int numberOfRuns = 1;
        Gson GSON = new Gson();

        String msg = "lkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvdalkdmnkaldmnkasnmdjkanfjksadnvlkmsfmfjoireghieughefvmkw;fowpew'dc';pc<LDLA;ldsaopfdsjkivgn hgfnb,mvzl/fvsdkfjsdipjgnubdnvgkmvl;x<mvl;kdskfjdnbklz;vkmkf[psdkfopsadjgvldpdskvpodasgfdjaspoidas;lfkmpfkmfjgjnkldrgn;;dagfm';das,mfdasgjmo;da'kgv';'das,m,fm;lasdmngopkdangdafv,as00rvda";


        HashMap<String, RTopic> key2Topic = new HashMap<>();
        try {

            RedissonClient client = Redisson.create();

            for (int i = 0; i < numberOfChannels; i++) {
                key2Topic.put(getChannelName(i), client.getTopic(getChannelName(i)));
            }


            while (true) {
                long now = System.currentTimeMillis();
                for (int i = 0; i < numberOfChannels; i++) {
                    Message m = Message.create(String.valueOf(i * numberOfRuns), msg);
                    key2Topic.get(getChannelName(i)).publish(GSON.toJson(m));
                }
                long end = System.currentTimeMillis();

                while(System.currentTimeMillis() - now < 300){
                    Thread.sleep(10);
                    System.out.println("sleeping");
                }

                System.out.println("Total millis for send: " + (end-now));
                numberOfRuns++;

                if (numberOfRuns % 100 == 0)
                    System.out.println("Published: " + numberOfRuns
                            + " times to each channel, total msgs: " + (numberOfRuns * numberOfChannels));

            }
//            client.shutdown();
        } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    private static String getChannelName(int channelId) {
        return "Channel_" + channelId;
    }

}