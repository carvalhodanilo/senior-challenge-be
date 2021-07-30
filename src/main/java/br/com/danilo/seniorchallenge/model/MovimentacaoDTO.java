package br.com.danilo.seniorchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoDTO {

    private Long id;
    private QuartoDTO quarto;
    private PessoaDTO pessoa;
    private Date entrada;
    private Date saida;
    private Long valorTotal;
    private boolean closed;
    private boolean garagem;

    public static MovimentacaoDTO converter(Movimentacao movimentacao){
        var m = new MovimentacaoDTO();
        m.setId(movimentacao.getId());
        m.setPessoa(new PessoaDTO(movimentacao.getPessoa().getId(),
                                  movimentacao.getPessoa().getNome(),
                                  movimentacao.getPessoa().getCpf(),
                                  movimentacao.getPessoa().getTelefone()));

        m.setQuarto(new QuartoDTO(movimentacao.getQuarto().getId(),
                                  movimentacao.getQuarto().getNome(),
                                  movimentacao.getQuarto().getPreco(),
                                  movimentacao.getQuarto().getImagem(), true));

        m.setEntrada(movimentacao.getEntrada());
        m.setClosed(movimentacao.isClosed());
        m.setGaragem(movimentacao.isGaragem());
        m.setSaida(movimentacao.getSaida());
        m.setValorTotal(movimentacao.isClosed()? movimentacao.getValorTotal() : m.calcularValorAtual(m.countDaysBetween(movimentacao.getEntrada(), new Date())));
        return m;
    }

    private DaysParams countDaysBetween(final Date startDate, final Date endDate) {
        DaysParams days = new DaysParams();

        LocalDate startDateAsLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateAsLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Long noOfDaysBetween = startDateAsLocalDate.until(endDateAsLocalDate, ChronoUnit.DAYS);

        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        List<LocalDate> businessDays = startDateAsLocalDate.datesUntil(endDateAsLocalDate)
                .filter(isWeekend.negate())
                .collect(Collectors.toList());

        days.setDiasMeioFDS((long) businessDays.size());
        days.setDiasFDS(noOfDaysBetween - (long) businessDays.size());

        return days;
    }

    private Long calcularValorAtual(DaysParams dias){
        Long valorDiarias = (dias.getDiasMeioFDS() * 120) + (dias.getDiasFDS() * 150);
        if(this.isGaragem()) {
            var valorGaragem = (dias.getDiasMeioFDS() * 15) + (dias.getDiasFDS() * 20);
            valorDiarias = valorGaragem + valorDiarias;
        }
        return valorDiarias;
    }
}
@Data
class DaysParams {
    Long diasFDS;
    Long diasMeioFDS;
}
