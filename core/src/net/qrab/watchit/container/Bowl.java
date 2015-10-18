package net.qrab.watchit.container;

import com.badlogic.gdx.physics.box2d.*;

public class Bowl {

	Body body;
	ChainShape chainShape;

	public Bowl(World world) {

		chainShape = new ChainShape();
		chainShape.createChain(new float[] {-10,5,-10,-1,10,1,10,5});

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		body = world.createBody(bodyDef);
		body.createFixture(chainShape, 1f);
	}

	public void update(float delta) {

	}


}

