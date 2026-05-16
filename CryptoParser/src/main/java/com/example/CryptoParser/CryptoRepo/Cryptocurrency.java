package com.example.CryptoParser.CryptoRepo;
import java.math.BigDecimal;
import java.util.Objects;
public class Cryptocurrency {
    private long id;
    private String name;
    private String symbol;
    private String slug;
    private BigDecimal circulatingSupply;
    private BigDecimal priceUSD;
    private BigDecimal marketCapUSD;

    public Cryptocurrency(long id, String name, String symbol, String slug,
                          BigDecimal circulatingSupply, BigDecimal priceUSD,
                          BigDecimal marketCapUSD) {
        validateID(id);
        validateName(name);
        validateSymbol(symbol);
        validateSlug(slug);
        validateCirculatingSupply(circulatingSupply);
        validateMarketCap(marketCapUSD);
        validatePrice(priceUSD);
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.circulatingSupply = circulatingSupply;
        this.priceUSD = priceUSD;
        this.marketCapUSD = marketCapUSD;
    }

    public long getId() {
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

    public BigDecimal getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(BigDecimal circulatingSupply) {
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

    private void validateID(long id){
        if(id < 0L){
            throw new IllegalArgumentException("*/Id/* не может быть отрицательным");
        }
    }
    private void validateName(String name){
        if(name.isEmpty()){
            throw new IllegalArgumentException("*/name/* не может быть пустым");
        }
    }
    private void validateSymbol(String symbol){
        if(symbol.isEmpty()){
            throw new IllegalArgumentException("*/symbol/* не может быть пустым");
        }
    }
    private void validateSlug(String slug){
        if(slug.isEmpty()){
            throw new IllegalArgumentException("*/slug/* не может быть пустым");
        }
    }
    private void validateCirculatingSupply(BigDecimal circulatingSupply){
        if(circulatingSupply.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("*/circulatingSupply/* не может быть отрицательным");
        }
    }
    private void validatePrice(BigDecimal priceUSD){
        if(priceUSD.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("*/priceUSD/* не может быть отрицательным");
        }
    }
    private void validateMarketCap(BigDecimal marketCapUSD){
        if(marketCapUSD.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("*/marketCap/* не может быть отрицательным");
        }
    }
    @Override
    public String toString(){
        return  "\nID: " + id +
                "\nName: " + name +
                "\nSymbol: " + symbol +
                "\nSlug: " + slug +
                "\nPrice (USD): " + priceUSD +
                "\nMarke Cap. (USD): " + marketCapUSD +
                "\nCirculating supply: " + circulatingSupply + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cryptocurrency that = (Cryptocurrency) o;
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