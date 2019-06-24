package com.example.firstdemoandroid.giorgio.customs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.firstdemoandroid.giorgio.Helper.Nodo;

public class CustomViewEdge extends View {

    private Paint paint;
    private Paint corner;
    private Nodo n1;
    private Nodo n2;
    Context context;

    public CustomViewEdge(Context context, Nodo n1, Nodo n2) {
        super(context);
        this.context = context;
        this.n1 = n1;
        this.n2 = n2;
        paint = new Paint();
        corner = new Paint();
        corner.setColor(Color.BLACK);
        corner.setStrokeWidth(15);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //drawTriangle(canvas,n1.getX(),n1.getY(), Color.GREEN);

        //drawSequenceOfPoints(canvas,n1.getX(),n1.getY(),n2.getX(),n2.getY());
        canvas.drawLine(n1.getX(),n1.getY(),n2.getX(),n2.getY(),paint);

    }

    /*private void drawTriangle(Canvas canvas, int xPoint, int yPoint, int color){


        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(15);


        Point a = new Point(xPoint, yPoint);
        Point b = new Point(xPoint, yPoint+50);
        Point c = new Point(xPoint+50, yPoint+50);

        canvas.drawLine(a.x,a.y,b.x,b.y, paint);
        canvas.drawLine(b.x,b.y,c.x,c.y, paint);
        canvas.drawLine(a.x,a.y,c.x,c.y, paint);

        System.out.println("A "+ a.toString() + " B " +b.toString() +  " C " +c.toString());



    }*/

    private void drawSequenceOfPoints(Canvas canvas, int x1, int y1, int x2, int y2){
        int temp = 0;
        if(x1 == x2){
            if(y1>y2 ){
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
            while (y1 < y2-50) {
                y1 += 50;
                canvas.drawCircle(x1,y1,15,corner);
                canvas.drawCircle(x1,y1,10,paint);
                //canvas.drawPoint(x1, y1, paint);
            }

        }
        else if(y1 == y2){
            if(x1>x2){
                temp = x1;
                x1 = x2;
                x2 = temp;
            }
            while (x1 < x2-50) {
                x1 += 50;
                canvas.drawCircle(x1,y1,15,corner);
                canvas.drawCircle(x1,y1,10,paint);
                //canvas.drawPoint(x1, y1, paint);
            }
        }
        else{
            System.out.println("Sono qua");
            int xDifference, yDifference;
            if(x1>x2){
                temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 =y2;
                y2 = temp;
            }
            yDifference = y2 - y1;
            xDifference = x2 - x1;


            int x = x1;
            while (x<x2-30){
                float y = findY(x,x1,y1,xDifference,yDifference);

                System.out.println("La X vale: "+x);
                System.out.println("La Y vale: "+y);
                x += 50;

                canvas.drawCircle(x,y,15,corner);
                canvas.drawCircle(x,y,10,paint);
                //canvas.drawPoint(x1, y, paint);
            }
        }

    }

    private float findY(int x, int x1, int y1, int xDifference, int yDifference){

        float yDifferenceFloat = (float) yDifference;
        float xDifferenceFloat = (float) xDifference;
        float moltiplier = (float) (x-x1);

        //System.out.println("Sto calcolando la Y "+ (yDifferenceFloat/xDifferenceFloat)* moltiplier);

        return ((yDifferenceFloat/xDifferenceFloat)* moltiplier) + y1;
    }

}
