package net.qrab.watchit.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Actor {

	private static final int HEAD = 0;
	private static final int NECK = 1;
	private static final int TORS = 2;
	private static final int LEGS = 3;
	private static final int FEET = 4;

	private static final float WALKFORCE = 10f;
	private static final float JUMPFORCE = 500f;

	public enum bodyZone {
		HEAD,
		FEET
	}

	private boolean        grounded  = false;
	private int            feetCount = 0;
	private Array<Body>    feetTouchingBodies;
	private int            lastTouch;

	private Body           body;

	private Fixture        headFixture;
	private Fixture        torsFixture;
	private Fixture        feetFixture;

	private PolygonShape   headShape;
	private PolygonShape   torsShape;
	private PolygonShape   feetShape;

	private float          walk          = 0;
	private float          jump          = 0;

	public Player(World world) {
		feetTouchingBodies = new Array<Body>();

		headShape = new PolygonShape();
		torsShape = new PolygonShape();
		feetShape = new PolygonShape();

		headShape.setAsBox(0.10f, 0.05f, new Vector2(0.00f, 1.50f), 0);
		torsShape.setAsBox(0.25f, 0.75f, new Vector2(0.00f, 0.75f), 0);
		feetShape.setAsBox(0.20f, 0.05f, new Vector2(0.00f, 0.00f), 0);

		body = world.createBody(new BodyDef());
		body.setTransform(0f,1f,0f);
		body.setType(BodyDef.BodyType.DynamicBody);
		body.setFixedRotation(true);
		body.setLinearDamping(1);
		body.setGravityScale(2f);
		body.setUserData(this);

		headFixture = body.createFixture(headShape, 1f);
		torsFixture = body.createFixture(torsShape, 1f);
		feetFixture = body.createFixture(feetShape, 1f);

		headFixture.setSensor(true);
		headFixture.setUserData(bodyZone.HEAD);

		feetFixture.setSensor(true);
		feetFixture.setUserData(bodyZone.FEET);

	}

	public void addFeetContact() {
		feetCount++;
	}

	public void update(float delta) {
		if(jump!=0) jump*=.5;
		if(jump<1)  jump=0;
		body.applyForceToCenter(walk,jump,true);
	}

	public void processKeyDown(int k) {
		if(k==Input.Keys.D) walk += WALKFORCE;
		if(k==Input.Keys.A) walk -= WALKFORCE;
		if(k==Input.Keys.W) {
			if(grounded) {
				jump = JUMPFORCE;
				setGrounded(false);
			}
		}
	}

	public void processKeyUp(int k) {
		if(k==Input.Keys.D) walk = 0;
		if(k==Input.Keys.A) walk = 0;
		if(k==Input.Keys.W) jump = 0;
	}

	public Vector2 getWorldCenter() {
		return body.getWorldCenter();
	}

	public void setGrounded(boolean g) {
		grounded = g;
	}

	public void processTouchDown(float xpercent, float ypercent) {
		System.out.println("x%: " + xpercent + ", y%: " + ypercent);
		lastTouch = 0;
		if(xpercent>0.75f && ypercent>0.75f) lastTouch = Input.Keys.D;
		if(xpercent<0.25f && ypercent>0.75f) lastTouch = Input.Keys.A;
		processKeyDown(lastTouch);
	}

	public void processTouchUp() {
		processKeyUp(lastTouch);
	}

}
