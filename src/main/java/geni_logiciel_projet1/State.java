package geni_logiciel_projet1;

public class State {
    private boolean uping;
    private boolean downing;

    public State(){
        this.uping = false;
        this.downing = false;
    }

    public void UpdateState(int action){
        switch(action){
            case 1 :    {
                            this.uping = true;
                            this.downing = false;
                        }
                        break;
            case 0 :    {
                            this.uping = false;
                            this.downing = false;
                        }
                        break;
            case -1 :   {
                            this.uping = false;
                            this.downing = true;
                        }
                        break;
            default :   break;
        }
    }

    public boolean isUping(){
        return this.uping;
    }

    public boolean isDowning(){
        return this.downing;
    }

    public boolean isMoving(){
        return this.uping || this.downing;
    }
}
