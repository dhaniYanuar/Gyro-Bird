package com.mygdx.finalproject.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.finalproject.Main;

/**
 * Created by Brent on 6/26/2015.
 */
public class MenuState extends State{
    private Texture background;
    //Play Button
    private Sprite playBtn;
    //CreditButton
    private Sprite creditBtn;
    //Exit Button
    private Sprite exitBtn;
    //Tittle
    private Texture tittle;

    private BitmapFont font;
    private String Best = "Best Score : ";
    private String myScore = String.valueOf(Main.score);

    private int x1, y1;
    private Vector3 input;

    public MenuState(final GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Main.WIDTH / 2, Main.HEIGHT / 2);
        font = new BitmapFont(Gdx.files.internal("wood.fnt"));
        //Background
        background = new Texture("UIBackground.png");
        //Title
        tittle = new Texture("TITLE.png");
        //---------------Button----------------//
        //playBtn
        playBtn = new Sprite(new Texture("play.png"));
        playBtn.setBounds(cam.position.x - playBtn.getWidth()/2, cam.position.y, playBtn.getWidth(),playBtn.getHeight());

        //creditBtn
        creditBtn = new Sprite(new Texture("credit2.png"));
        creditBtn.setBounds(cam.position.x - creditBtn.getWidth()/2, cam.position.y -50, creditBtn.getWidth(), creditBtn.getHeight());

        //exitBtn
        exitBtn = new Sprite(new Texture("exit.png"));
        exitBtn.setBounds(cam.position.x - exitBtn.getWidth()/2, cam.position.y -100, exitBtn.getWidth(), exitBtn.getHeight());

    }

    @Override
    public void handleInput() {
        x1 = Gdx.input.getX();
        y1 = Gdx.input.getY();
        input = new Vector3(x1, y1, 0);
        cam.unproject(input);
        if (playBtn.getBoundingRectangle().contains(input.x, input.y)){
            gsm.set(new HintState(gsm));
        }
        else if(creditBtn.getBoundingRectangle().contains(input.x,input.y)){
            gsm.set(new CreditState(gsm));
        }else if (exitBtn.getBoundingRectangle().contains(input.x, input.y)){
            Gdx.app.exit();
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
        sb.draw(tittle, cam.position.x - tittle.getWidth()/2, cam.position.y + 100);
        sb.draw(playBtn,cam.position.x - playBtn.getWidth()/2, cam.position.y );
        sb.draw(creditBtn, cam.position.x - creditBtn.getWidth()/2, cam.position.y -50);
        sb.draw(exitBtn, cam.position.x - exitBtn.getWidth()/2, cam.position.y -100);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        font.draw(sb, Best, cam.position.x/2f , cam.position.y+100);
        font.draw(sb, myScore, cam.position.x/1.05f , cam.position.y+70);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        tittle.dispose();
        System.out.println("Menu State Disposed");
    }
}
