package notebook.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Максим on 07.04.2017.
 */
@Entity
@Table(name = "events", schema = "notebook")
public class EventDataSet implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Date date;

    public EventDataSet() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //used for primary key value generation
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = true, length = 80)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "date", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventDataSet that = (EventDataSet) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
