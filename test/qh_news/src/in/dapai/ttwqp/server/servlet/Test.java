package in.dapai.ttwqp.server.servlet;

import org.json.JSONException;
import org.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		String result="{code:1,apps:[{id:1,name:'内测游戏',describe:'启航内部测试游戏,如有更新,请先更新,再进游戏',ui:'http://qhcdn.vlahao.com/app_data/ttwqp/icon_title_internal.png',list:[{id:1,pkg:'in.dapai.ttwqp',vc:5,etag:'D826EF034490E80FFD30A1B849C5EDFC',vn:'0.8.1',name:'天天玩棋牌',urlapk:'http://126.am/WNcn50',urlicon:'http://126.am/3Sc760',tag:'3',describe:'null',ad:0,urlpatch:'http://qhcdn.vlahao.com/apk/internal/patch',patch:'',patchs:},{id:2,pkg:'in.dapai.dz',vc:25,etag:'0C73CA3B97D4E8EAFD1DB8A92756BDF9',vn:'1.0',name:'内测德州扑克',urlapk:'http://126.am/GxdPu4',urlicon:'http://126.am/A9ak80',tag:'3',describe:'http://qhcdn.vlahao.com/pro_desc/dzsss.html',ad:6,urlpatch:'http://qhcdn.vlahao.com/apk/internal/patch',patch:'',patchs:null},{id:3,pkg:'in.dapai.ofc',vc:9,etag:'40763913F063C21974BF9E874F95AFEA',vn:'2.2.6',name:'内测ofc',urlapk:'http://126.am/xKBQI0',urlicon:'http://126.am/TmYj20',tag:'3',describe:'null',ad:6,urlpatch:'http://qhcdn.vlahao.com/apk/internal/patch',patch:'6,7,8,',patchs:[{u:'http://126.am/Svcsm4',vc:6},{u:'http://126.am/DQ2X71',vc:7},{u:'http://126.am/eLSSF3',vc:8}]},{id:33,pkg:'in.dapai.hpysz.test',vc:27,etag:'3572A93F2300DB8E959E4B70A10DBE38',vn:'1.92',name:'内测赢三张',urlapk:'http://126.am/d0OM74',urlicon:'http://126.am/VUhr12',tag:'3',describe:'null',ad:6,urlpatch:'http://qhcdn.vlahao.com/apk/internal/patch',patch:'24,25,',patchs:[{u:'http://126.am/QfKYp1',vc:24},{u:'http://126.am/WKY4z4',vc:25}]},{id:34,pkg:'in.dapai.nn',vc:35,etag:'4B88056E50F14DC66E49168DF281E33B',vn:'1.9',name:'内测斗牛',urlapk:'http://126.am/CHCtz3',urlicon:'http://126.am/g9I8y3',tag:'3',describe:'null',ad:6,urlpatch:'http://qhcdn.vlahao.com/apk/internal/patch',patch:'34,35,',patchs:[{u:'http://126.am/sQA8N3',vc:34},{u:'http://126.am/oZJUk4',vc:35}]}]}]} ";
				try{
			System.out.println(new JSONObject(result));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
