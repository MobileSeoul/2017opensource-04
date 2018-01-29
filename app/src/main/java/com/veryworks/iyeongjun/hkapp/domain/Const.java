package com.veryworks.iyeongjun.hkapp.domain;

/**
 * Created by iyeongjun on 2017. 9. 27..
 */

public class Const {
    public static class Auth{
        public static final String KEY = "i9opnT0CNWj0dfjeUmoProOy3c%2BqZNdfztvalVl624EISpMpkXLDvVzwuuA8n8BnYnMqOjKlZIoBQLm%2FpX%2Fyqg%3D%3D";

        public static final String GOOGLE_MAP_KEY ="AIzaSyDwXXYfmqAXfRkYNVu1znWmhh5jjqLWkWE";//"AIzaSyBFb9H1N2EmElNDAcVTXUKxJSGKtbpFU90";//"AIzaSyCXHmSY-KVXCkaPvyL9PVQUbi2RBBg4JY4";
    }
    public static class Count{
        public static final int TYPE_LENGTH = 7;
        public static final int SECTION_LENGTH = 11;
    }

    public static class GPS{
        public static final int GPS_MILE_SECOND = 100;
        public static final int GPS_MIN_LENGTH = 1;
        public static final int GPS_DEFAULT_RESULT = 1;

    }
    public static class ContentType{
        public static final int PARK = 5;
        public static final int REPO = 7;
        public static final int REST = 8;
        public static final int FOOD = 2;
        public static final int TOUR = 6;
        public static final int SHOP = 10;
        public static final int INN = 9;
        public static final int HIST = 11;
    }
    public static class FRAGMENT{
        public static final int LIST = 0;
        public static final int SECTION_AND_TYPE = 1;
        public static final int TOUNAMENT =2;
        public static final int MAP = 3;
    }
    public static class SECTION{

        public static final Section GANGNAM = new Section(1,"강남구",37.498881,127.051167);
        public static final Section GANGDONG = new Section(2,"강동구",37.554738,127.146599);
        public static final Section GANGSEO = new Section(3,"강서구",37.566876,126.835083);
        public static final Section GUANJIN = new Section(4,"광진구",37.547491,127.087243);
        public static final Section DONGJAK = new Section(5,"동작구",37.508410,126.950557);
        public static final Section MAPO = new Section(6,"마포구",37.554515,126.913792);
        public static final Section SEOCHO = new Section(7, "서초구",37.495348,127.008017);
        public static final Section SENGDONG = new Section(8,"성동구",37.552509,127.042697);
        public static final Section SONGPA = new Section(9,"송파구",37.513315,127.107941);
        public static final Section YONGSAN = new Section(10,"용산구",37.531060,126.983885);
        public static final Section YUNGDUNG = new Section(11,"영등포구",37.524097,126.912296);
    }
    public static class AR{
        public final static int REQUEST_CAMERA_PERMISSIONS_CODE = 11;
        public static final int REQUEST_LOCATION_PERMISSIONS_CODE = 0;

        public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
        public static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1; // 1 minute

        public static final boolean IN_IMAGE = true;
        public static final boolean OUT_IMAGE = false;
    }
    public static class NETWORK{
        public static final String SERVER_URL = "http://ec2-52-78-202-182.ap-northeast-2.compute.amazonaws.com:80";
        public static final String AWS_ACCESS_KEY_ID = "";
        public static final String AWS_SECRET_ACCESS_KEY = "";
        public static final String BUCKET_NAME = "ishkstorage";
        public static final String BUCKET_URL = "https://s3.ap-northeast-2.amazonaws.com/ishkstorage/";
    }

    public static class API_CHECK{
        public static final String START_WITH = "#3$";
        public static final String SPLIT_WITH = "^5$";
    }
}
