package com.example.CryptoParser.CryptoRepo;
import java.math.BigDecimal;
import java.util.Objects;

public class Сryptocurrency {
    private int id;
    private String name;
    private String symbol;
    private String slug;
    private long circulatingSupply;
    private BigDecimal priceUSD;
    private BigDecimal marketCapUSD;

    public Сryptocurrency(int id, String name, String symbol, String slug,
                          long circulatingSupply, BigDecimal priceUSD,
                          BigDecimal marketCapUSD) {
        validateID();
        validateName();
        validateSymbol();
        validateSlug();
        validateCirculatingSupply();
        validateMarketCap();
        validatePrice();
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.circulatingSupply = circulatingSupply;
        this.priceUSD = priceUSD;
        this.marketCapUSD = marketCapUSD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public long getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(long circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public BigDecimal getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(BigDecimal priceUSD) {
        this.priceUSD = priceUSD;
    }

    public BigDecimal getMarketCapUSD() {
        return marketCapUSD;
    }

    public void setMarketCapUSD(BigDecimal marketCapUSD) {
        this.marketCapUSD = marketCapUSD;
    }

    public void validateID(){
        if(this.id < 0){
            throw new IllegalArgumentException("*/Id/* не может быть отрицательным");
        }
    }
    public void validateName(){
        if(this.name.isEmpty()){
            throw new IllegalArgumentException("*/name/* не может быть пустым");
        }
    }
    public void validateSymbol(){
        if(this.symbol.isEmpty()){
            throw new IllegalArgumentException("*/symbol/* не может быть пустым");
        }
    }
    public void validateSlug(){
        if(this.slug.isEmpty()){
            throw new IllegalArgumentException("*/slug/* не может быть пустым");
        }
    }
    public void validateCirculatingSupply(){
        if(this.circulatingSupply < 0){
            throw new IllegalArgumentException("*/circulatingSupply/* не может быть отрицательным");
        }
    }
    public void validatePrice(){
        if(this.priceUSD.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("*/priceUSD/* не может быть отрицательным");
        }
    }
    public void validateMarketCap(){
        if(this.marketCapUSD.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("*/marketCap/* не может быть отрицательным");
        }
    }
    @Override
    public String toString(){
        return  "\n" + id +
                "\n" + name +
                "\n" + symbol +
                "\n" + slug +
                "\n" + priceUSD +
                "\n" + marketCapUSD +
                "\n" + circulatingSupply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Сryptocurrency that = (Сryptocurrency) o;
        return id == that.id &&
                circulatingSupply == that.circulatingSupply &&
                Objects.equals(name, that.name) &&
                Objects.equals(symbol, that.symbol) &&
                Objects.equals(slug, that.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol, slug);
    }
}

