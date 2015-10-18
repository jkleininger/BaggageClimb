package net.qrab.watchit.handlers;

import com.badlogic.gdx.physics.box2d.*;
import net.qrab.watchit.actors.Player;

public class MyContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		if(fixtureA.getUserData()!=null) {
			if(fixtureA.getUserData().equals(Player.bodyZone.FEET)) {
				((Player) fixtureA.getBody().getUserData()).setGrounded(true);
			} else if(fixtureA.getUserData().equals(Player.bodyZone.HEAD)) {
				System.out.println("bonk!");
			}
		}

		if(fixtureB.getUserData()!=null) {
			if(fixtureB.getUserData().equals(Player.bodyZone.FEET)) {
				((Player) fixtureB.getBody().getUserData()).setGrounded(true);
			} else if(fixtureB.getUserData().equals(Player.bodyZone.HEAD)) {
				System.out.println("bonk!");
			}
		}

	}

	private void beginContact1(Contact contact) {
		if(contact.getFixtureA().getBody().getUserData().getClass().equals(Player.class)) {
			if( contact.getFixtureA().getUserData().equals(Player.bodyZone.FEET )) {
				((Player)(contact.getFixtureA().getBody().getUserData())).addFeetContact();
			}
		}
	}


	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
