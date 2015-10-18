package net.qrab.watchit.actors;

import com.badlogic.gdx.physics.box2d.*;

import java.util.Random;

public class Block {

	private static final float  MAX_SPEED = 30f;
	private static final float  MAX_WD    = 0.5f;
	private static final float  MIN_WD    = 0.2f;
	private static final Random random    = new Random();

	PolygonShape  shape;
	Body          body;
	Fixture       fixture;

	public Block(World world) {
		shape = new PolygonShape();
		shape.setAsBox(
				MIN_WD + (random.nextFloat() * (MAX_WD - MIN_WD)),
				MIN_WD + (random.nextFloat() * (MAX_WD - MIN_WD))
		);

		body = world.createBody(new BodyDef());
		body.setTransform(
				0,
		        20,
		        0
		);
		body.setType(BodyDef.BodyType.DynamicBody);

		fixture = body.createFixture(shape,1f);

	}

	public void update(float delta) {

	}

}
