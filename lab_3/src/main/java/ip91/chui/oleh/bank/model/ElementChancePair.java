package ip91.chui.oleh.bank.model;

import ip91.chui.oleh.bank.service.element.Element;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ElementChancePair {

  private final Element element;
  private final double chance;

}
