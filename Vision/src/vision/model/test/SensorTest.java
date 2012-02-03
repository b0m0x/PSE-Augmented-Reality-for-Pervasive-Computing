package vision.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import vision.model.Position;
import vision.model.Sample;
import vision.model.Sensor;

public class SensorTest {

	@Test
	public void test() {
		List<Sample> l = new ArrayList<Sample>();
		Sample s = new Sample();
		s.setType("Temperatur");
		s.setUnit("°C");
		s.setUpdate(10000);
		l.add(new Sample());
		Sensor sensor = new Sensor("Sensor1", 500, l);

		List<String> tags = new ArrayList<String>();
		tags.add("heizung");
		tags.add("toll");
		sensor.setTags(tags);

		sensor.setPosition(new Position(1, 2, 3));

		assertEquals(500, sensor.getUpdate());
		assertEquals("Sensor1", sensor.getId());
		assertEquals(l, sensor.getSamples());

	}

}
