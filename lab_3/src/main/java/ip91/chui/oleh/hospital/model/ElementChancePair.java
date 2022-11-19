package ip91.chui.oleh.hospital.model;

import ip91.chui.oleh.hospital.service.element.base.Element;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ElementChancePair {

  private final Element element;
  private final double chance;

}
