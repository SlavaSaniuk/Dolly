package testentities;

public class DetailsEntity {

    private Integer detail_id;

    private String detail_name;

    public Integer getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(Integer detail_id) {
        this.detail_id = detail_id;
    }

    public String getDetail_name() {
        return detail_name;
    }

    public void setDetail_name(String detail_name) {
        this.detail_name = detail_name;
    }

    @Override
    public String toString() {
        return "DetailsEntity{" +
                "detail_id=" + detail_id +
                ", detail_name='" + detail_name + '\'' +
                '}';
    }
}
