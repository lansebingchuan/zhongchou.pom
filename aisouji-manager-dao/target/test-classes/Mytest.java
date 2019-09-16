import com.aisouji.bean.Permission;

public class Mytest {
	for (Permission permission0 : lists) {
		Permission childer = permission0;
		if (childer.getPid() == null) {//如果为根节点
			root.add(childer);
			childer.setOpen(true);
		}else {
			for (Permission permission1 : lists) {
				if (permission1.getId() == childer.getPid()) {
					Permission parent = permission1;
					parent.getChildren().add(childer);
					break;
				}
			}
		}
}
