package info.u250.c2d.physical.box2d.loader.cbt.serializers;

import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;

import com.badlogic.gdx.utils.Json;
/**
 * @author lycying@gmail.com
 */
public class CircleSerializer implements Json.Serializer<CircleData> {
	@SuppressWarnings({"rawtypes" })
	@Override
	public void write(Json json, CircleData object,  Class knownType) {
		json.writeObjectStart();
		json.writeValue("type", "circle");
		json.writeValue("radius", object.radius);
		json.writeValue("density", object.density);
		json.writeValue("friction", object.friction);
		json.writeValue("restitution", object.restitution);
		json.writeValue("angle", object.angle);
		json.writeValue("centerX", object.center.x);
		json.writeValue("centerY", object.center.y);
		json.writeValue("isSensor", object.isSensor);
		json.writeValue("isDynamic", object.isDynamic);
		json.writeValue("categoryBits", object.filter_categoryBits);
		json.writeValue("groupIndex", object.filter_groupIndex);
		json.writeValue("maskBits", object.filter_maskBits);
		json.writeValue("name", object.name == null ? "":object.name);
		json.writeValue("res", object.res == null ? "" : object.res);
		json.writeValue("data", object.data == null ? "" : object.data);
		json.writeObjectEnd();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public CircleData read(Json json, Object jsonData, Class type) {
		final float radius = json.readValue("radius", Float.class,jsonData);
		final float density = json.readValue("density", Float.class,jsonData);
		final float friction = json.readValue("friction", Float.class,jsonData);
		final float restitution = json.readValue("restitution", Float.class,jsonData);
		final float angle = json.readValue("angle", Float.class,jsonData);
		final float centerX = json.readValue("centerX", Float.class,jsonData);
		final float centerY = json.readValue("centerY", Float.class,jsonData);
		final boolean isSensor = json.readValue("isSensor", Boolean.class,jsonData);
		final boolean isDynamic = json.readValue("isDynamic", Boolean.class,jsonData);
		final int filter_categoryBits = json.readValue("categoryBits", Integer.class,jsonData);
		final int filter_groupIndex = json.readValue("groupIndex", Integer.class,jsonData);
		final int filter_maskBits = json.readValue("maskBits", Integer.class,jsonData);
		final String name = json.readValue("name", String.class,jsonData);
		final String res = json.readValue("res", String.class,jsonData);
		final String datax = json.readValue("data", String.class,jsonData);
		
		final CircleData data = new CircleData();
		data.data = datax;
		data.res = res;
		data.radius = radius;
		data.density = density;
		data.friction = friction;
		data.restitution = restitution;
		data.angle = angle;
		data.center.set(centerX, centerY);
		data.isDynamic = isDynamic;
		data.isSensor = isSensor;
		data.filter_categoryBits = filter_categoryBits;
		data.filter_groupIndex = filter_groupIndex;
		data.filter_maskBits = filter_maskBits;
		data.name = name;

		return data;
	}
}
