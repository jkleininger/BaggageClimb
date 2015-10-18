package net.qrab.watchit;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import net.qrab.watchit.actors.Block;
import net.qrab.watchit.actors.Player;
import net.qrab.watchit.container.Bowl;
import net.qrab.watchit.handlers.MyContactListener;

public class WatchitGame extends InputAdapter implements ApplicationListener {

	private float  timeBetweenBlocks    = 2.0f;
	private float  timeSinceLastBlock   = 0.0f;
	private int    maxBlocks            = 100;

	private World  world;
	private Bowl   bowl;
	private Player player;

	private Array<Block>       blocks;

	private Box2DDebugRenderer b2dr;
	private OrthographicCamera cam;
	private ExtendViewport     evp;

	@Override
	public void create () {
		world  = new World(new Vector2(0,-10),false);
		bowl   = new Bowl(world);
		b2dr   = new Box2DDebugRenderer();
		cam    = new OrthographicCamera(800,480);
		evp    = new ExtendViewport(20,12,cam);
		player = new Player(world);

		blocks = new Array<Block>();

		world.setContactListener(new MyContactListener());

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void resize(int width, int height) {
		evp.update(width, height);
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		cam.position.set(player.getWorldCenter().x, player.getWorldCenter().y,0);
		cam.update();
		Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		b2dr.render(world, cam.combined);
	}

	public void update(float delta) {
		world.step(1 / 60f, 8, 6);
		timeSinceLastBlock+=delta;
		if(blocks.size < maxBlocks && timeSinceLastBlock >= timeBetweenBlocks) {
			blocks.add(new Block(world));
			timeSinceLastBlock=0;
		}
		for(Block b : blocks) {
			b.update(delta);
		}
		player.update(delta);
		bowl.update(delta);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean keyDown(int keycode) {
		player.processKeyDown(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		player.processKeyUp(keycode);
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		player.processTouchDown((float)screenX / Gdx.graphics.getWidth(), (float)screenY / Gdx.graphics.getHeight());
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		player.processTouchUp();
		return true;
	}

}
