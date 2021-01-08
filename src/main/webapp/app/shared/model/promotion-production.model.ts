import { IProduction } from 'app/shared/model/production.model';
import { IPromotion } from 'app/shared/model/promotion.model';

export interface IPromotionProduction {
  id?: number;
  production?: IProduction;
  promotion?: IPromotion;
}

export const defaultValue: Readonly<IPromotionProduction> = {};
