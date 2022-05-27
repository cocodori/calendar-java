package com.calendar.api.dto;

import com.calendar.core.util.Period;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Data
@Builder
public class EngagementEmailStuff {
    private static final String engagementUpdateApi = "http://localhost:8080/events/engagements/";
    public static final String MAIL_TIME_FORMAT = "yyyy년 MM월 dd일(E) a hh시 mm분";
    public static final List<Pair<String, Predicate<Period>>> endAtFormatParts = Arrays.asList(
            Pair.of("yyyy년 ", period -> period.getEndAt().getYear() == period.getStartAt().getYear()),
            Pair.of("MM월 ", period -> period.getEndAt().getMonth() == period.getStartAt().getMonth()),
            Pair.of("dd일(E) ", period -> period.getEndAt().getDayOfMonth() == period.getStartAt().getDayOfMonth())
    );
    private final Long engagementId;
    private final String toEmail;
    private final List<String> attendeeEmails;
    private final String title;
    private final Period period;

    public static String getEndAtFormat(Period period,
                                        String format,
                                        List<Pair<String,Predicate<Period>>> remainEndAtFormatParts) {
        if (remainEndAtFormatParts.isEmpty()) {
            return format;
        } else if (remainEndAtFormatParts.get(0).getSecond().test(period)) {
            return getEndAtFormat(
                    period,
                    format.replace(remainEndAtFormatParts.get(0).getFirst(), ""),
                    remainEndAtFormatParts.subList(1, remainEndAtFormatParts.size())
            );
        } else {
            return format;
        }
    }

    public String getSubject() {
        return new StringBuilder()
                .append("[초대장] ")
                .append(" - ")
                .append(period.toString())
                .append("(")
                .append(toEmail)
                .append(")")
                .toString();
    }

    public Map<String,Object> getProps() {
        Map<String, Object> props = new HashMap<>();
        props.put("title", title);
        props.put("calendar", toEmail);
        props.put("period", period.toString());
        props.put("attendees", attendeeEmails);
        props.put("acceptUrl", engagementUpdateApi + engagementId + "?type=ACCEPT");
        props.put("rejectUrl", engagementUpdateApi + engagementId + "?type=REJECT");

        return props;
    }
}
