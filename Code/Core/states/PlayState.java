package com.mygdx.finalproject.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import com.mygdx.finalproject.Main;
import com.mygdx.finalproject.sprites.Bird;
import com.mygdx.finalproject.sprites.Tube;

import static com.mygdx.finalproject.Main.*;

/**
 * Created by Brent on 7/5/2015.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;

    private Array<Tube> tubes;
    private BitmapFont font;
    private int y1=Main.HEIGHT/2-10;
    public int score ;
    public String myScore ="Score :"  + String.valueOf(score);

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        cam.setToOrtho(false, WIDTH / 2, HEIGHT / 2);

        font = new BitmapFont(Gdx.files.internal("wood.fnt"));
        bg = new Texture("bggameplay.png");

        tubes = new Array<Tube>();

        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.getGyroscopeY() < -1)
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for(int i = 0; i < tubes.size; i++){
            Tube tube = tubes.get(i);

            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x  + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                score++;
                myScore = "Score : " +String.valueOf(score);

            }

            if(tube.collides(bird.getBounds())){
                Main.score = score;
                gsm.set(new MenuState(gsm));
            }
        }
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        Gdx.gl.glClearColor(1, 1, 1, 1);
        //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        font.draw(sb, myScore, cam.position.x, y1);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        font.dispose();
        bird.dispose();
        //ground.dispose();
        for(Tube tube : tubes)
            tube.dispose();

        System.out.println("Play State Disposed");
    }


}
