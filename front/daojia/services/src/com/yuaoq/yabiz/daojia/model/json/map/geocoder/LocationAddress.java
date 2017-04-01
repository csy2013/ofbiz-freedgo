package com.yuaoq.yabiz.daojia.model.json.map.geocoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/25.
 */
public class LocationAddress {
    
    
    /**
     * status : 0
     * message : query ok
     * request_id : 6185653597229832908
     * result : {"location":{"lat":32.130989,"lng":118.723359},"address":"江苏省南京市浦口区大桥北路9号丽岛路","formatted_addresses":{"recommend":"浦口区丽岛路北弘阳·旭日华庭(西区)内","rough":"浦口区丽岛路北弘阳·旭日华庭(西区)内"},"address_component":{"nation":"中国","province":"江苏省","city":"南京市","district":"浦口区","street":"丽岛路","street_number":"大桥北路9号"},"ad_info":{"adcode":"320111","name":"中国,江苏省,南京市,浦口区","location":{"lat":32.130989,"lng":118.723358},"nation":"中国","province":"江苏省","city":"南京市","district":"浦口区"},"address_reference":{"famous_area":{"title":"桥北","location":{"lat":32.12944,"lng":118.724457},"_distance":0,"_dir_desc":"内"},"crossroad":{"title":"火炬南路/丽岛路(路口)","location":{"lat":32.129398,"lng":118.721992},"_distance":213.4,"_dir_desc":"东北"},"town":{"title":"泰山街道","location":{"lat":32.130989,"lng":118.723358},"_distance":0,"_dir_desc":"内"},"street_number":{"title":"大桥北路9号","location":{"lat":32.129429,"lng":118.724442},"_distance":1.9,"_dir_desc":""},"street":{"title":"丽岛路","location":{"lat":32.129341,"lng":118.723274},"_distance":178.6,"_dir_desc":"北"},"landmark_l2":{"title":"弘阳·旭日华庭(西区)","location":{"lat":32.130989,"lng":118.723358},"_distance":0,"_dir_desc":"内"}},"poi_count":10,"pois":[{"id":"16138089182785017514","title":"弘阳·旭日华庭(西区)","address":"江苏省南京市浦口区","category":"房产小区;住宅区;住宅小区","location":{"lat":32.130989,"lng":118.723358},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":0,"_dir_desc":"内"},{"id":"1892330248890145971","title":"弘阳·旭日华庭","address":"江苏省南京市浦口区丽岛路","category":"房产小区;住宅区;住宅小区","location":{"lat":32.130989,"lng":118.723358},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":0,"_dir_desc":"内"},{"id":"10088018829119287944","title":"比华利园区","address":"江苏省南京市浦口区","category":"房产小区;住宅区;住宅小区","location":{"lat":32.130978,"lng":118.722694},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":61.8,"_dir_desc":"东"},{"id":"13756101207845170364","title":"金城丽景","address":"江苏省南京市浦口区大桥北路33号","category":"房产小区;住宅区;住宅小区","location":{"lat":32.13229,"lng":118.723366},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":145.2,"_dir_desc":"南"},{"id":"8400100000000003374211","title":"旭日华庭西公交站","address":"江苏省南京市浦口区旭日华庭西公交站","category":"普通公交线路","location":{"lat":32.131397,"lng":118.721977},"ad_info":{"adcode":"320100","province":"","city":"","district":""},"_distance":137.9,"_dir_desc":"东"},{"id":"4952491514793238255","title":"旭日爱上城","address":"江苏省南京市浦口区星火南路2号","category":"房产小区;住宅区;住宅小区","location":{"lat":32.130955,"lng":118.721718},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":154.7,"_dir_desc":"附近"},{"id":"18136144604042159013","title":"华侨城","address":"江苏省南京市浦口区大桥北路1号","category":"房产小区;住宅区;住宅小区","location":{"lat":32.129128,"lng":118.724197},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":221.8,"_dir_desc":"西北"},{"id":"11922519924387502784","title":"中浦家园","address":"江苏省南京市浦口区大桥北路1号","category":"房产小区;住宅区;住宅小区","location":{"lat":32.129158,"lng":118.724892},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":249.9,"_dir_desc":"西北"},{"id":"11693570133194035711","title":"金棕榈小区","address":"江苏省南京市浦口区","category":"房产小区;住宅区;住宅小区","location":{"lat":32.132008,"lng":118.728516},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":499.2,"_dir_desc":"西南"},{"id":"1574972724555607558","title":"南京市浦口区人民检察院反贪局","address":"江苏省南京市浦口区大桥北路5-8号","category":"机构团体;政府机关","location":{"lat":32.129055,"lng":118.726044},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":332.3,"_dir_desc":"西北"}]}
     */
    
    public int status;
    public String message;
    public String request_id;
    /**
     * location : {"lat":32.130989,"lng":118.723359}
     * address : 江苏省南京市浦口区大桥北路9号丽岛路
     * formatted_addresses : {"recommend":"浦口区丽岛路北弘阳·旭日华庭(西区)内","rough":"浦口区丽岛路北弘阳·旭日华庭(西区)内"}
     * address_component : {"nation":"中国","province":"江苏省","city":"南京市","district":"浦口区","street":"丽岛路","street_number":"大桥北路9号"}
     * ad_info : {"adcode":"320111","name":"中国,江苏省,南京市,浦口区","location":{"lat":32.130989,"lng":118.723358},"nation":"中国","province":"江苏省","city":"南京市","district":"浦口区"}
     * address_reference : {"famous_area":{"title":"桥北","location":{"lat":32.12944,"lng":118.724457},"_distance":0,"_dir_desc":"内"},"crossroad":{"title":"火炬南路/丽岛路(路口)","location":{"lat":32.129398,"lng":118.721992},"_distance":213.4,"_dir_desc":"东北"},"town":{"title":"泰山街道","location":{"lat":32.130989,"lng":118.723358},"_distance":0,"_dir_desc":"内"},"street_number":{"title":"大桥北路9号","location":{"lat":32.129429,"lng":118.724442},"_distance":1.9,"_dir_desc":""},"street":{"title":"丽岛路","location":{"lat":32.129341,"lng":118.723274},"_distance":178.6,"_dir_desc":"北"},"landmark_l2":{"title":"弘阳·旭日华庭(西区)","location":{"lat":32.130989,"lng":118.723358},"_distance":0,"_dir_desc":"内"}}
     * poi_count : 10
     * pois : [{"id":"16138089182785017514","title":"弘阳·旭日华庭(西区)","address":"江苏省南京市浦口区","category":"房产小区;住宅区;住宅小区","location":{"lat":32.130989,"lng":118.723358},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":0,"_dir_desc":"内"},{"id":"1892330248890145971","title":"弘阳·旭日华庭","address":"江苏省南京市浦口区丽岛路","category":"房产小区;住宅区;住宅小区","location":{"lat":32.130989,"lng":118.723358},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":0,"_dir_desc":"内"},{"id":"10088018829119287944","title":"比华利园区","address":"江苏省南京市浦口区","category":"房产小区;住宅区;住宅小区","location":{"lat":32.130978,"lng":118.722694},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":61.8,"_dir_desc":"东"},{"id":"13756101207845170364","title":"金城丽景","address":"江苏省南京市浦口区大桥北路33号","category":"房产小区;住宅区;住宅小区","location":{"lat":32.13229,"lng":118.723366},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":145.2,"_dir_desc":"南"},{"id":"8400100000000003374211","title":"旭日华庭西公交站","address":"江苏省南京市浦口区旭日华庭西公交站","category":"普通公交线路","location":{"lat":32.131397,"lng":118.721977},"ad_info":{"adcode":"320100","province":"","city":"","district":""},"_distance":137.9,"_dir_desc":"东"},{"id":"4952491514793238255","title":"旭日爱上城","address":"江苏省南京市浦口区星火南路2号","category":"房产小区;住宅区;住宅小区","location":{"lat":32.130955,"lng":118.721718},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":154.7,"_dir_desc":"附近"},{"id":"18136144604042159013","title":"华侨城","address":"江苏省南京市浦口区大桥北路1号","category":"房产小区;住宅区;住宅小区","location":{"lat":32.129128,"lng":118.724197},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":221.8,"_dir_desc":"西北"},{"id":"11922519924387502784","title":"中浦家园","address":"江苏省南京市浦口区大桥北路1号","category":"房产小区;住宅区;住宅小区","location":{"lat":32.129158,"lng":118.724892},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":249.9,"_dir_desc":"西北"},{"id":"11693570133194035711","title":"金棕榈小区","address":"江苏省南京市浦口区","category":"房产小区;住宅区;住宅小区","location":{"lat":32.132008,"lng":118.728516},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":499.2,"_dir_desc":"西南"},{"id":"1574972724555607558","title":"南京市浦口区人民检察院反贪局","address":"江苏省南京市浦口区大桥北路5-8号","category":"机构团体;政府机关","location":{"lat":32.129055,"lng":118.726044},"ad_info":{"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"},"_distance":332.3,"_dir_desc":"西北"}]
     */
    
    public Result result;
    
    public static LocationAddress objectFromData(String str) {
        
        return new Gson().fromJson(str, LocationAddress.class);
    }
    
    public static LocationAddress objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), LocationAddress.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<LocationAddress> arrayLocationAddressFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<LocationAddress>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class Result {
        /**
         * lat : 32.130989
         * lng : 118.723359
         */
        
        public Location location;
        public String address;
        /**
         * recommend : 浦口区丽岛路北弘阳·旭日华庭(西区)内
         * rough : 浦口区丽岛路北弘阳·旭日华庭(西区)内
         */
        
        public FormattedAddresses formatted_addresses;
        /**
         * nation : 中国
         * province : 江苏省
         * city : 南京市
         * district : 浦口区
         * street : 丽岛路
         * street_number : 大桥北路9号
         */
        
        public AddressComponent address_component;
        /**
         * adcode : 320111
         * name : 中国,江苏省,南京市,浦口区
         * location : {"lat":32.130989,"lng":118.723358}
         * nation : 中国
         * province : 江苏省
         * city : 南京市
         * district : 浦口区
         */
        
        public AdInfo ad_info;
        /**
         * famous_area : {"title":"桥北","location":{"lat":32.12944,"lng":118.724457},"_distance":0,"_dir_desc":"内"}
         * crossroad : {"title":"火炬南路/丽岛路(路口)","location":{"lat":32.129398,"lng":118.721992},"_distance":213.4,"_dir_desc":"东北"}
         * town : {"title":"泰山街道","location":{"lat":32.130989,"lng":118.723358},"_distance":0,"_dir_desc":"内"}
         * street_number : {"title":"大桥北路9号","location":{"lat":32.129429,"lng":118.724442},"_distance":1.9,"_dir_desc":""}
         * street : {"title":"丽岛路","location":{"lat":32.129341,"lng":118.723274},"_distance":178.6,"_dir_desc":"北"}
         * landmark_l2 : {"title":"弘阳·旭日华庭(西区)","location":{"lat":32.130989,"lng":118.723358},"_distance":0,"_dir_desc":"内"}
         */
        
        public AddressReference address_reference;
        public int poi_count;
        /**
         * id : 16138089182785017514
         * title : 弘阳·旭日华庭(西区)
         * address : 江苏省南京市浦口区
         * category : 房产小区;住宅区;住宅小区
         * location : {"lat":32.130989,"lng":118.723358}
         * ad_info : {"adcode":"320111","province":"江苏省","city":"南京市","district":"浦口区"}
         * _distance : 0
         * _dir_desc : 内
         */
        
        public List<Pois> pois;
        
        public static Result objectFromData(String str) {
            
            return new Gson().fromJson(str, Result.class);
        }
        
        public static Result objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), Result.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<Result> arrayResultFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<Result>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
        
        public static class Location {
            public double lat;
            public double lng;
            
            public static Location objectFromData(String str) {
                
                return new Gson().fromJson(str, Location.class);
            }
            
            public static Location objectFromData(String str, String key) {
                
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    
                    return new Gson().fromJson(jsonObject.getString(str), Location.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            public static List<Location> arrayLocationFromData(String str) {
                
                Type listType = new TypeToken<ArrayList<Location>>() {
                }.getType();
                
                return new Gson().fromJson(str, listType);
            }
        }
        
        public static class FormattedAddresses {
            public String recommend;
            public String rough;
            
            public static FormattedAddresses objectFromData(String str) {
                
                return new Gson().fromJson(str, FormattedAddresses.class);
            }
            
            public static FormattedAddresses objectFromData(String str, String key) {
                
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    
                    return new Gson().fromJson(jsonObject.getString(str), FormattedAddresses.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            public static List<FormattedAddresses> arrayFormattedAddressesFromData(String str) {
                
                Type listType = new TypeToken<ArrayList<FormattedAddresses>>() {
                }.getType();
                
                return new Gson().fromJson(str, listType);
            }
        }
        
        public static class AddressComponent {
            public String nation;
            public String province;
            public String city;
            public String district;
            public String street;
            public String street_number;
            
            public static AddressComponent objectFromData(String str) {
                
                return new Gson().fromJson(str, AddressComponent.class);
            }
            
            public static AddressComponent objectFromData(String str, String key) {
                
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    
                    return new Gson().fromJson(jsonObject.getString(str), AddressComponent.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            public static List<AddressComponent> arrayAddressComponentFromData(String str) {
                
                Type listType = new TypeToken<ArrayList<AddressComponent>>() {
                }.getType();
                
                return new Gson().fromJson(str, listType);
            }
        }
        
        public static class AdInfo {
            public String adcode;
            public String name;
            public Location location;
            public String nation;
            public String province;
            public String city;
            public String district;
            
            public static AdInfo objectFromData(String str) {
                
                return new Gson().fromJson(str, AdInfo.class);
            }
            
            public static AdInfo objectFromData(String str, String key) {
                
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    
                    return new Gson().fromJson(jsonObject.getString(str), AdInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            public static List<AdInfo> arrayAdInfoFromData(String str) {
                
                Type listType = new TypeToken<ArrayList<AdInfo>>() {
                }.getType();
                
                return new Gson().fromJson(str, listType);
            }
        }
        
        public static class AddressReference {
            /**
             * title : 桥北
             * location : {"lat":32.12944,"lng":118.724457}
             * _distance : 0
             * _dir_desc : 内
             */
            
            public FamousArea famous_area;
            public FamousArea crossroad;
            public FamousArea town;
            public FamousArea street_number;
            public FamousArea street;
            public FamousArea landmark_l2;
            
            public static AddressReference objectFromData(String str) {
                
                return new Gson().fromJson(str, AddressReference.class);
            }
            
            public static AddressReference objectFromData(String str, String key) {
                
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    
                    return new Gson().fromJson(jsonObject.getString(str), AddressReference.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            public static List<AddressReference> arrayAddressReferenceFromData(String str) {
                
                Type listType = new TypeToken<ArrayList<AddressReference>>() {
                }.getType();
                
                return new Gson().fromJson(str, listType);
            }
            
            public static class FamousArea {
                public String title;
                public Location location;
                public Double _distance;
                public String _dir_desc;
                
                public static FamousArea objectFromData(String str) {
                    
                    return new Gson().fromJson(str, FamousArea.class);
                }
                
                public static FamousArea objectFromData(String str, String key) {
                    
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        
                        return new Gson().fromJson(jsonObject.getString(str), FamousArea.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    
                    return null;
                }
                
                public static List<FamousArea> arrayFamousAreaFromData(String str) {
                    
                    Type listType = new TypeToken<ArrayList<FamousArea>>() {
                    }.getType();
                    
                    return new Gson().fromJson(str, listType);
                }
            }
        }
        
        public static class Pois {
            public String id;
            public String title;
            public String address;
            public String category;
            public Location location;
            public AdInfo ad_info;
            public Double _distance;
            public String _dir_desc;
            
            public static Pois objectFromData(String str) {
                
                return new Gson().fromJson(str, Pois.class);
            }
            
            public static Pois objectFromData(String str, String key) {
                
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    
                    return new Gson().fromJson(jsonObject.getString(str), Pois.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            public static List<Pois> arrayPoisFromData(String str) {
                
                Type listType = new TypeToken<ArrayList<Pois>>() {
                }.getType();
                
                return new Gson().fromJson(str, listType);
            }
        }
    }
}
