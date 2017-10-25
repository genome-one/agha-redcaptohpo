package au.org.garvan.kccg.agha.redcap.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by tudor on 15/03/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientEntryDataTransformDto implements Serializable {

    private static final long serialVersionUID = 6619352470749465424L;

    @NotNull
    @JsonProperty("data")
    private final Map<String, String> data;

    public PatientEntryDataTransformDto(@JsonProperty("data") Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PatientEntryDataTransformDto that = (PatientEntryDataTransformDto) o;

        return new EqualsBuilder()
                .append(data, that.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(data)
                .toHashCode();
    }

}
