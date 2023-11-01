package com.br.senac.apiclientebackend.modelo;

public class DataResponse {
    private Object rows;

    private InfoRow infoRows;

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public InfoRow getInfoRows() {
        return infoRows;
    }

    public void setInfoRows(InfoRow infoRows) {
        this.infoRows = infoRows;
    }
}
