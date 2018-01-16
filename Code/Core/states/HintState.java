package com.mygdx.finalproject.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.finalproject.Main;

/**
 * Created by GameDev on 1/16/2018.
 */

public class HintState extends State{
    private Texture background;

    //CreditButton
    private Sprite play;

    private int x1, y1;
    private Vector3 input;

    public HintState(final GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, 288 , 512 );

        //Background
        background = new Texture("Hint.png");



        //creditBtn
        play = new Sprite(new Texture("next.png"));
        play.setBounds(cam.position.x - play.getWidth()/2 +100, cam.position.y -200, play.getWidth(), play.getHeight());

    }

    @Override
    public void handleInput() {
        x1 = Gdx.input.getX();
        y1 = Gdx.input.getY();
        input = new Vector3(x1, y1, 0);
        cam.unproject(input);
        if (play.getBoundingRectangle().contains(input.x, input.y)){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt)
    {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(play, cam.position.x - play.getWidth()/2 + 100, cam.position.y -200);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        System.out.println("Hint State Disposed");
    }
}
