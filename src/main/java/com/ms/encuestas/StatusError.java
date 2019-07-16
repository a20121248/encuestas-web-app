package com.ms.encuestas;

final class StatusError {

    private static StatusError singleton = null;
    private int status = 0;

    public static StatusError getInstance() {
        singleton = new StatusError();
        return singleton;
    }

    public void FATAL() {
        this.status = 4;
    }

    public void ERROR() {
        if (this.status < 3) {
            this.status = 3;
        }
    }

    public void WARNING() {
        if (this.status < 2) {
            this.status = 2;
        }
    }

    public void INFO() {
        if (this.status < 1) {
            this.status = 1;
        }
    }

    public void reset() {
        this.status = 0;
    }

    public String levelError() {
        String estado;
        switch (this.status) {
            case 0:
                estado = "OK";
                break;
            case 1:
                estado = "INFO";
                break;
            case 2:
                estado = "WARN";
                break;
            case 3:
                estado = "ERROR";
                break;
            case 4:
                estado = "FATAL";
                break;
            default:
                estado = "UNKNOWN";
        }
        return estado;
    }

    public int valueError() {
        return this.status;
    }
}
