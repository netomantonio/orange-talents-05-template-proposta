package br.com.zup.nossocartao.biometrias.requests;

import br.com.zup.nossocartao.biometrias.models.Biometria;
import br.com.zup.nossocartao.cartoes.models.Cartao;
import br.com.zup.nossocartao.errors.ErrorsResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@JsonAutoDetect
public class BiometriaRequest {

    @NotEmpty
    private String imgBiometria;

    @JsonCreator
    public BiometriaRequest(String imgBiometria) {
        this.imgBiometria = imgBiometria;
    }

    public BiometriaRequest() {
    }

    public String getImgBiometria() {
        return imgBiometria;
    }

    public void setImgBiometria(String imgBiometria) {
        this.imgBiometria = imgBiometria;
    }

    public List<ErrorsResponse> verificaBase64() {
        List<ErrorsResponse> errorsResponses = new ArrayList<>();
        Base64.Decoder decoder = Base64.getDecoder();

        try {

            decoder.decode(this.imgBiometria);

        } catch (Exception ex) {

            errorsResponses.add(new ErrorsResponse("imgBiometria", "A biometria " + this.imgBiometria
                    + " não está codificada na base64"));

        }

        return errorsResponses;
    }

    public List<Biometria> toModel(Cartao cartao) {

        List<Biometria> biometrias = new ArrayList<>();

        biometrias.add(new Biometria(this.imgBiometria, cartao));

        return biometrias;
    }
}
