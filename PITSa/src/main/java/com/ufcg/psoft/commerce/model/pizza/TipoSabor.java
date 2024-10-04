package PITSa.src.main.java.com.ufcg.psoft.commerce.model.pizza;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoSabor {
    DOCE,
    SALGADO;

    @JsonCreator
    public static TipoSabor from(String value) {
        try {
            return TipoSabor.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
//            throw new TipoSaborInvalidException("Valor de tipo invalido para " + value);
            return null;
        }
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
